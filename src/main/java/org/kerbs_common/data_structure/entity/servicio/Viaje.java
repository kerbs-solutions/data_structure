package org.kerbs_common.data_structure.entity.servicio;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.kerbs_common.data_structure.entity.facturable.Movimiento;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * S050: Entity representing operational trip groupings - NOT a Servicio.
 * 
 * A Viaje groups multiple Movimientos (heladera movements) performed in a single trip.
 * This is purely operational - the actual client services are the individual Movimientos.
 * 
 * Cost Management:
 * - kerbs-operations manages: trip costs (combustible, otros gastos, chofer)
 * - Revenue comes from the individual Movimientos, not from the Viaje itself
 * 
 * State Management: Simple operational states:
 * - estado: PENDIENTE → EN_PROCESO → REALIZADO/FALLIDO
 * - estadoOperacion: PENDIENTE_INFORME → INFORMADA  
 * - estadoContable: handled by informes microservice
 */
@Entity
@Table(name = "viaje")
@Getter
@Setter
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Viaje operational states (NOT servicio states)
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoServicio estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_operacion")
    private EstadoOperacion estadoOperacion;

    // estadoContable field removed - handled by informes microservice

    // Trip execution dates
    @Column(name = "viaje_fecha_inicio")
    private LocalDate fechaInicio;
    
    @Column(name = "viaje_fecha_fin") 
    private LocalDate fechaFin;

    @Column(name = "viaje_fecha_resolucion")
    private LocalDate fechaResolucion;

    // Trip personnel
    @Column(name = "viaje_chofer")
    private String chofer;

    // Trip cost tracking (managed by operations)
    @Column(name = "viaje_litros_combustible")
    private Double litrosCombustible;
    
    @Column(name = "viaje_valor_combustible")
    private Double valorCombustible;
    
    @Column(name = "viaje_valor_otros_gastos")
    private Double valorOtrosGastos;
    
    @Column(name = "viaje_detalle_otros_gastos")
    private String detalleOtrosGastos;


    // Metadata
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationship to movimientos (lazy to avoid N+1 queries and mapping issues)
    @OneToMany(mappedBy = "viaje", fetch = FetchType.LAZY)
    private List<Movimiento> movimientos;

    /**
     * Constructor for new viaje with essential trip information.
     * @param estado Initial estado (typically PENDIENTE)
     * @param fechaInicio Trip start date
     * @param chofer Driver name/identifier
     */
    public Viaje(@NonNull EstadoServicio estado, LocalDate fechaInicio, String chofer) {
        this.estado = estado;
        this.estadoOperacion = EstadoOperacion.PENDIENTE_INFORME;
        // estadoContable handled by informes microservice
        this.fechaInicio = fechaInicio;
        this.chofer = chofer;
        this.movimientos = new ArrayList<>();
    }

    /**
     * Constructor for viaje with full trip details.
     */
    public Viaje(@NonNull EstadoServicio estado, LocalDate fechaInicio, LocalDate fechaFin,
                 String chofer, Double litrosCombustible, Double valorCombustible, 
                 Double valorOtrosGastos, String detalleOtrosGastos) {
        this.estado = estado;
        this.estadoOperacion = EstadoOperacion.PENDIENTE_INFORME;
        // estadoContable handled by informes microservice
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.chofer = chofer;
        this.litrosCombustible = litrosCombustible;
        this.valorCombustible = valorCombustible;
        this.valorOtrosGastos = valorOtrosGastos;
        this.detalleOtrosGastos = detalleOtrosGastos;
        this.movimientos = new ArrayList<>();
    }

    /**
     * Default constructor for JPA.
     */
    protected Viaje() {
        this.movimientos = new ArrayList<>();
    }

    /**
     * Add a movimiento to this viaje.
     * @param movimiento The movement to add
     */
    public void addMovimiento(Movimiento movimiento) {
        movimiento.setViaje(this);
        this.movimientos.add(movimiento);
    }

    /**
     * Calculate total trip costs (NOT revenue - that's handled by informes).
     * This includes combustible costs and otros gastos.
     * @return Total costs for this trip
     */
    public double getPrecioTotal() {
        double totalCosts = 0.0;
        
        // Add combustible costs
        if (valorCombustible != null) {
            totalCosts += valorCombustible;
        }
        
        // Add otros gastos
        if (valorOtrosGastos != null) {
            totalCosts += valorOtrosGastos;
        }
        
        return totalCosts;
    }

    /**
     * Get the number of movimientos in this viaje.
     * Note: This method does NOT load the movimientos collection to avoid lazy loading issues.
     * Use repository-level counting queries for accurate counts.
     * @return 0 (actual count should be calculated by repositories)
     */
    public int getMovimientosCount() {
        // Avoid triggering lazy loading of movimientos collection
        // Repository services should calculate this value separately
        return 0;
    }

    /**
     * Check if viaje is ready to be processed by informes.
     * @return true if viaje is completed and needs billing processing
     */
    public boolean isPendienteInforme() {
        return (this.estado == EstadoServicio.REALIZADO || this.estado == EstadoServicio.FALLIDO) 
                && this.estadoOperacion == EstadoOperacion.PENDIENTE_INFORME;
    }
}
