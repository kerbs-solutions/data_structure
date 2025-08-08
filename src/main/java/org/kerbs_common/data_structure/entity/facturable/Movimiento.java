package org.kerbs_common.data_structure.entity.facturable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.kerbs_common.data_structure.entity.Cliente;
import org.kerbs_common.data_structure.entity.Heladera;
import org.kerbs_common.data_structure.entity.HeladeraUbicacion;
import org.kerbs_common.data_structure.entity.servicio.*;

/**
 * S050: Entity representing individual heladera movements within a viaje.
 * 
 * A Movimiento represents moving one heladera from one location to another
 * as part of a larger viaje (trip). Multiple movimientos can be grouped
 * in a single viaje for efficiency.
 * 
 * Key Business Rules:
 * - When movimiento reaches REALIZADO state → automatically update heladera's current ubicacion
 * - Cliente reference is denormalized for less than 3 heladeras business rule in informes
 * - valorTotal is set by informes microservice, NOT visible in operations UI
 * 
 * State Management: Inherits triple-state system from Servicio base class:
 * - estadoServicio: PENDIENTE → EN_PROCESO → REALIZADO/FALLIDO (inherited)
 * - estadoOperacion: PENDIENTE_INFORME → INFORMADA (inherited)
 * - estadoContable: PENDIENTE_PROFORMA → ... → COBRADA (inherited)
 */
@Entity
@Table(name = "movimiento")
@Getter
@Setter
public class Movimiento extends Servicio {

    // S050: Updated to use stable internal ID instead of composite key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movimiento_heladera_internal_id", referencedColumnName = "heladera_internal_id")
    @NonNull
    private Heladera heladera;

    // S050: Denormalized cliente reference for informes business rules
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movimiento_cliente_id", referencedColumnName = "cliente_id")
    @NonNull
    private Cliente cliente;

    // Location references for movement origin and destination  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ubicacion_origen_id", referencedColumnName = "ubicacion_id")
    private HeladeraUbicacion ubicacionHeladeraOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ubicacion_destino_id", referencedColumnName = "ubicacion_id")
    private HeladeraUbicacion ubicacionHeladeraDestino;

    @Column(name = "movimiento_fecha_movimiento")
    private java.time.LocalDate fechaMovimiento;

    @Column(name = "movimiento_km_excedente")
    private Double kmExcedente;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viaje_id", referencedColumnName = "id")
    private Viaje viaje; // Nullable for standalone PENDIENTE movimientos

    /**
     * Constructor for new movimiento with essential information.
     * @param heladera The heladera being moved
     * @param cliente The cliente who owns the heladera (denormalized)
     * @param ubicacionDestino The destination location for the heladera
     * @param viaje The viaje this movimiento belongs to
     * @param fechaMovimiento The planned date for this movement
     */
    public Movimiento(@NonNull Heladera heladera, @NonNull Cliente cliente, 
                     @NonNull HeladeraUbicacion ubicacionDestino, @NonNull Viaje viaje,
                     java.time.LocalDate fechaMovimiento) {
        // Initialize inherited Servicio fields
        super(EstadoServicio.PENDIENTE);
        
        this.heladera = heladera;
        this.cliente = cliente;
        this.ubicacionHeladeraOrigen = heladera.getCurrentUbicacion().orElse(null);
        this.ubicacionHeladeraDestino = ubicacionDestino;
        this.viaje = viaje;
        this.fechaMovimiento = fechaMovimiento;
    }

    /**
     * Constructor with km excedente information.
     */
    public Movimiento(@NonNull Heladera heladera, @NonNull Cliente cliente,
                     @NonNull HeladeraUbicacion ubicacionDestino, @NonNull Viaje viaje,
                     java.time.LocalDate fechaMovimiento, Double kmExcedente) {
        this(heladera, cliente, ubicacionDestino, viaje, fechaMovimiento);
        this.kmExcedente = kmExcedente;
    }

    /**
     * Constructor for standalone movimientos (without viaje) - starts in PENDIENTE state.
     * @param heladera The heladera being moved
     * @param cliente The cliente who owns the heladera (denormalized)
     * @param ubicacionDestino The destination location for the heladera
     * @param fechaMovimiento The planned date for this movement
     */
    public Movimiento(@NonNull Heladera heladera, @NonNull Cliente cliente, 
                     @NonNull HeladeraUbicacion ubicacionDestino,
                     java.time.LocalDate fechaMovimiento) {
        // Initialize inherited Servicio fields
        super(EstadoServicio.PENDIENTE);
        
        this.heladera = heladera;
        this.cliente = cliente;
        this.ubicacionHeladeraOrigen = heladera.getCurrentUbicacion().orElse(null);
        this.ubicacionHeladeraDestino = ubicacionDestino;
        this.viaje = null; // Standalone movimiento
        this.fechaMovimiento = fechaMovimiento;
    }

    /**
     * Default constructor for JPA.
     */
    protected Movimiento() {
    }

    /**
     * Advance estado servicio to next state if possible.
     * @return Current estado after advancement
     */
    public EstadoServicio siguienteEstadoServicio() {
        EstadoServicio siguienteEstado = this.getEstado().getSiguienteEstado();
        if (siguienteEstado != null) {
            this.setEstado(siguienteEstado);
            
            // S050: Critical business rule - update heladera ubicacion when completing
            if (this.getEstado() == EstadoServicio.REALIZADO) {
                updateHeladeraUbicacion();
            }
        }
        return this.getEstado();
    }

    /**
     * Advance estado operacion to next state if possible.
     */
    public EstadoOperacion siguienteEstadoOperacion() {
        EstadoOperacion siguienteEstado = this.getEstadoOperacion().getSiguienteEstado();
        if (siguienteEstado != null) {
            this.setEstadoOperacion(siguienteEstado);
        }
        return this.getEstadoOperacion();
    }


    /**
     * S050: Critical business rule implementation.
     * When movimiento reaches REALIZADO state, automatically update heladera's current ubicacion.
     */
    private void updateHeladeraUbicacion() {
        if (this.ubicacionHeladeraDestino != null && this.heladera != null) {
            // Mark current ubicacion as inactive
            this.heladera.getCurrentUbicacion().ifPresent(currentUbicacion -> {
                currentUbicacion.finalizarPlacement(java.time.LocalDate.now());
            });
            
            // Update heladera's denormalized ubicacion fields
            this.heladera.setUbicacionActualNumeroCliente(this.ubicacionHeladeraDestino.getNumeroCliente());
            this.heladera.setUbicacionActualRazonSocial(this.ubicacionHeladeraDestino.getClienteRazonSocial());
            this.heladera.setUbicacionActualDireccion(this.ubicacionHeladeraDestino.getClienteDireccion());
            this.heladera.setUbicacionActualLocalidad(this.ubicacionHeladeraDestino.getClienteLocalidad());
            this.heladera.setUbicacionActualComodato(this.ubicacionHeladeraDestino.getNumeroComodato());
        }
    }

    /**
     * Check if movimiento is ready to be processed by informes.
     */
    public boolean isPendienteInforme() {
        return (this.getEstado() == EstadoServicio.REALIZADO || this.getEstado() == EstadoServicio.FALLIDO) 
                && this.getEstadoOperacion() == EstadoOperacion.PENDIENTE_INFORME;
    }

    /**
     * S050: Movimiento is facturable - pricing calculation handled by informes microservice.
     * Operations doesn't calculate pricing - that's handled by informes.
     */
    public double getPrecioTotal() {
        // S050: Operations doesn't calculate pricing - that's handled by informes
        // Return 0 as operations doesn't manage pricing values
        return 0.0;
    }
    
    /**
     * Get the cliente this movimiento is billed to.
     * @return Cliente for billing purposes
     */
    public Cliente getClienteFacturable() {
        return this.cliente;
    }
}
