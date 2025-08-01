package org.kerbs_common.data_structure.entity.servicio;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
public abstract class Servicio{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servicio_seq")
    @SequenceGenerator(name = "servicio_seq", sequenceName = "kerbs_operations.acondicionamiento_servicio_id_seq", allocationSize = 1)
    @Column(name = "servicio_id")
    private int id;

    @NonNull
    @Column(name = "servicio_estado")
    @Enumerated(EnumType.STRING)
    @Setter
    private EstadoServicio estado;

    @NonNull
    @Column(name = "servicio_estado_contable")
    @Enumerated(EnumType.STRING)
    @Setter
    private EstadoContableServicio estadoContable;

    @NonNull
    @Column(name = "estado_operacion")
    @Enumerated(EnumType.STRING)
    @Setter
    private EstadoOperacion estadoOperacion;

    @Column(name = "acondicionamiento_nro_solicitud")
    @Setter
    private int numeroSolicitud;

    /**
     * Constructor for new service with initial estado.
     * @param estadoServicio Initial service estado
     */
    public Servicio(@NonNull EstadoServicio estadoServicio) {
        this.estado = estadoServicio;
        this.estadoContable = EstadoContableServicio.PENDIENTE_PROFORMA;
        this.estadoOperacion = EstadoOperacion.PENDIENTE_INFORME;
    }

    /**
     * Constructor for service with ID only.
     * @param id Service ID
     */
    public Servicio(int id){
        this.id = id;
        this.estado = EstadoServicio.EN_PROCESO;
        this.estadoContable = EstadoContableServicio.PENDIENTE_PROFORMA;
        this.estadoOperacion = EstadoOperacion.PENDIENTE_INFORME;
    }
    
    /**
     * Constructor for service with all basic properties.
     * @param id Service ID
     * @param estadoServicio Service estado
     * @param servicioSolicitud Service solicitud number
     */
    public Servicio(int id, EstadoServicio estadoServicio, int servicioSolicitud){
        this.id = id;
        this.estado = estadoServicio;
        this.estadoContable = EstadoContableServicio.PENDIENTE_PROFORMA;
        this.estadoOperacion = EstadoOperacion.PENDIENTE_INFORME;
        this.numeroSolicitud = servicioSolicitud;
    }

    protected Servicio() {
        this.estado = EstadoServicio.EN_PROCESO;
        this.estadoContable = EstadoContableServicio.PENDIENTE_PROFORMA;
        this.estadoOperacion = EstadoOperacion.PENDIENTE_INFORME;
    }

    /**
     * Advances to next service estado if possible.
     * @return Current estado after advancement
     */
    public EstadoServicio siguienteEstadoServicio() {
        EstadoServicio siguienteEstado = this.estado.getSiguienteEstado();
        if(siguienteEstado!=null){
            this.estado = siguienteEstado;
        }

        return this.estado;
    }

    /**
     * Advances to next contable estado if possible.
     * @return Current contable estado after advancement
     */
    public EstadoContableServicio siguienteEstadoContableServicio() {
        EstadoContableServicio siguienteEstado = this.estadoContable.getSiguienteEstado();
        if(siguienteEstado!=null){
            this.estadoContable = siguienteEstado;
        }

        return this.estadoContable;
    }

    /**
     * Advances to next operation estado if possible.
     * @return Current operation estado after advancement
     */
    public EstadoOperacion siguienteEstadoOperacion() {
        EstadoOperacion siguienteEstado = this.estadoOperacion.getSiguienteEstado();
        if(siguienteEstado!=null){
            this.estadoOperacion = siguienteEstado;
        }

        return this.estadoOperacion;
    }

    public abstract double getPrecioTotal();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Servicio servicio)) return false;
        return id == servicio.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}


