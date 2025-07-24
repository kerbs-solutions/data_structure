package org.kerbs_common.data_structure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.kerbs_common.data_structure.entity.facturable.Movimiento;
import org.kerbs_common.data_structure.entity.servicio.Acondicionamiento;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "heladera", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"heladera_marca", "heladera_nro_serie"}, 
                           name = "uq_heladera_marca_serie"),
           @UniqueConstraint(columnNames = {"heladera_cliente_id", "heladera_activo"}, 
                           name = "idx_unique_owner_activo")
       })
@Getter
public class Heladera {
    // New stable internal identity - survives equipment part changes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heladera_internal_id")
    @Setter
    private Long internalId;

    // Equipment tracking identifiers - now unique constraint instead of primary key
    @Column(name = "heladera_marca", nullable = false)
    @Setter
    private String marca;
    
    @Column(name = "heladera_nro_serie", nullable = false) 
    @Setter
    private String serie;


    @OneToMany(mappedBy = "heladera")
    List<Movimiento> movimientos;
    @OneToMany(mappedBy = "heladera", cascade = CascadeType.ALL)
    List<Acondicionamiento> acondicionamientos;
    
    // Cliente relationship (can be Empresa or ClienteFinal)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "heladera_cliente_id", referencedColumnName = "cliente_id")
    @Setter
    private Cliente cliente;

    @Column(name = "heladera_activo")
    @Setter
    private String activo;

    @Column(name = "heladera_modelo")
    @Setter
    private String modelo;

    // S047: Ubicacion data moved to heladera_ubicacion table
    // One-to-many relationship to track placement history
    @OneToMany(mappedBy = "heladera", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("fechaInicio DESC") // Most recent placements first
    private List<HeladeraUbicacion> ubicaciones = new ArrayList<>();

    // V011: Denormalized current ubicacion columns for fast queries
    // These columns store current location data directly in heladera table for performance
    // Full historical data is maintained in heladera_ubicacion relationship
    @Column(name = "heladera_ubicacion_actual_numero_cliente")
    @Setter
    private String ubicacionActualNumeroCliente;

    @Column(name = "heladera_ubicacion_actual_razon_social")
    @Setter
    private String ubicacionActualRazonSocial;

    @Column(name = "heladera_ubicacion_actual_direccion")
    @Setter
    private String ubicacionActualDireccion;

    @Column(name = "heladera_ubicacion_actual_localidad")
    @Setter
    private String ubicacionActualLocalidad;

    @Column(name = "heladera_ubicacion_actual_comodato")
    @Setter
    private String ubicacionActualComodato;

    @Column(name = "heladera_en_taller")
    @Setter
    private boolean enTaller;

    @ManyToOne
    @JoinColumn(name = "heladera_tipo_id")
    @Setter
    private TipoHeladera tipo;

    //todo: agregar datos de contacto al parsear terrand

    protected Heladera() {
        this.movimientos = new ArrayList<>();
        this.acondicionamientos = new ArrayList<>();
        this.ubicaciones = new ArrayList<>();
    }

    /**
     * S047: Updated constructor - ubicacion data moved to HeladeraUbicacion table
     */
    public Heladera(Cliente cliente, String serie, String activo, String marca, String modelo, boolean enTaller) {
        this.cliente = cliente;
        this.serie = serie;
        this.activo = activo;
        this.marca = marca;
        this.modelo = modelo;
        this.enTaller = enTaller;
        this.movimientos = new ArrayList<>();
        this.acondicionamientos = new ArrayList<>();
        this.ubicaciones = new ArrayList<>();
    }

    public Heladera(Cliente cliente, String serie, String activo, String marca, String modelo, boolean enTaller, TipoHeladera tipo) {
        this(cliente, serie, activo, marca, modelo, enTaller);
        this.tipo = tipo;
    }

    public void addAcondcionamiento(Acondicionamiento acondicionamiento) {
        acondicionamiento.setHeladera(this);
        acondicionamientos.add(acondicionamiento);
    }

    // S047: Business methods for ubicaciones management

    /**
     * Get all ubicaciones ordered by fecha_inicio DESC (most recent first)
     */
    public List<HeladeraUbicacion> getUbicaciones() {
        return ubicaciones;
    }

    /**
     * Get the current active ubicacion (where heladera is currently placed)
     */
    public Optional<HeladeraUbicacion> getCurrentUbicacion() {
        return ubicaciones.stream()
                .filter(HeladeraUbicacion::isActive)
                .findFirst();
    }

    /**
     * Get historical ubicaciones (previous placements)
     */
    public List<HeladeraUbicacion> getHistoricalUbicaciones() {
        return ubicaciones.stream()
                .filter(HeladeraUbicacion::isHistorical)
                .toList();
    }

    /**
     * Add a new ubicacion placement
     */
    public void addUbicacion(HeladeraUbicacion ubicacion) {
        ubicacion.setHeladera(this);
        ubicaciones.add(ubicacion);
    }

    /**
     * Get current cliente information (from active ubicacion, not owner)
     */
    public Optional<String> getCurrentClienteRazonSocial() {
        return getCurrentUbicacion().map(HeladeraUbicacion::getClienteRazonSocial);
    }

    /**
     * Get current address (from active ubicacion)
     */
    public Optional<String> getCurrentDireccion() {
        return getCurrentUbicacion().map(HeladeraUbicacion::getClienteDireccion);
    }

    /**
     * Get current locality (from active ubicacion)
     */
    public Optional<String> getCurrentLocalidad() {
        return getCurrentUbicacion().map(HeladeraUbicacion::getClienteLocalidad);
    }

    // V011: Getter methods for denormalized ubicacion columns
    // These provide fast access to current location data without joins

    /**
     * Get current numero cliente from denormalized column (fast performance)
     */
    public String getUbicacionActualNumeroCliente() {
        return ubicacionActualNumeroCliente;
    }

    /**
     * Get current razon social from denormalized column (fast performance)
     */
    public String getUbicacionActualRazonSocial() {
        return ubicacionActualRazonSocial;
    }

    /**
     * Get current direccion from denormalized column (fast performance)
     */
    public String getUbicacionActualDireccion() {
        return ubicacionActualDireccion;
    }

    /**
     * Get current localidad from denormalized column (fast performance)
     */
    public String getUbicacionActualLocalidad() {
        return ubicacionActualLocalidad;
    }

    /**
     * Get current comodato from denormalized column (fast performance)
     */
    public String getUbicacionActualComodato() {
        return ubicacionActualComodato;
    }

    // Backward compatibility methods for composite key operations
    
    /**
     * Returns the composite key representation (marca, serie) for backward compatibility
     * @deprecated Use internalId for stable identity. This method is maintained for legacy compatibility only.
     */
    @Deprecated(since = "v009", forRemoval = false)
    public HeladeraId getCompositeId() {
        return new HeladeraId(marca, serie);
    }
    
    /**
     * Creates a new Heladera with composite key compatibility
     * @deprecated Use constructor without ubicacion data - ubicaciones managed separately in S047
     */
    @Deprecated(since = "v010", forRemoval = false) 
    public static Heladera createWithCompositeKey(String marca, String serie, Cliente cliente, String activo, 
                                                 String modelo, String numeroCliente, String razonSocial, 
                                                 String direccion, String localidad, boolean enTaller) {
        // S047: ubicacion data ignored - must be managed through HeladeraUbicacion
        return new Heladera(cliente, serie, activo, marca, modelo, enTaller);
    }

    /**
     * Finds heladera by composite key - for migration period compatibility
     * @deprecated Repository methods should use findByInternalId for stable identity
     */
    @Deprecated(since = "v009", forRemoval = false)
    public boolean matchesCompositeKey(String marca, String serie) {
        return this.marca.equals(marca) && this.serie.equals(serie);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Heladera heladera = (Heladera) obj;
        
        // Use internal ID for equality if both have it
        if (internalId != null && heladera.internalId != null) {
            return internalId.equals(heladera.internalId);
        }
        
        // Fallback to composite key for backward compatibility
        return marca != null ? marca.equals(heladera.marca) : heladera.marca == null &&
               serie != null ? serie.equals(heladera.serie) : heladera.serie == null;
    }

    @Override
    public int hashCode() {
        // Use internal ID for hashing if available
        if (internalId != null) {
            return internalId.hashCode();
        }
        
        // Fallback to composite key hash
        int result = marca != null ? marca.hashCode() : 0;
        result = 31 * result + (serie != null ? serie.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Heladera{" +
                "internalId=" + internalId +
                ", marca='" + marca + '\'' +
                ", serie='" + serie + '\'' +
                ", activo='" + activo + '\'' +
                ", modelo='" + modelo + '\'' +
                ", enTaller=" + enTaller +
                ", currentUbicacion=" + getCurrentClienteRazonSocial().orElse("Sin ubicación activa") +
                '}';
    }

}
