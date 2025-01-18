package org.kerbs_common.data_structure.entity.servicio;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
public abstract class Servicio{

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servicio_seq")
//    @SequenceGenerator(name = "servicio_seq", sequenceName = "servicio_id_seq", allocationSize = 1)
    @Column(name = "servicio_id")
    private int id;

    @NonNull
    @Column(name = "servicio_estado")
    @Enumerated(EnumType.STRING)
    private EstadoServicio estado;

    @NonNull
    @Column(name = "servicio_estado_contable")
    @Enumerated(EnumType.STRING)
    private EstadoContableServicio estadoContable;

    @Column(name = "acondicionamiento_nro_solicitud")
    private int numeroSolicitud;



    public Servicio(@NonNull EstadoServicio estadoServicio) {
        this.estado = estadoServicio;
        this.estadoContable = EstadoContableServicio.PENDIENTE_PROFORMA;
    }

    public Servicio(int id){
        this.id = id;
        this.estadoContable = EstadoContableServicio.PENDIENTE_PROFORMA;
    }
    public Servicio(int id, EstadoServicio estadoServicio, int servicioSolicitud){
        this.id = id;
        this.estado = estadoServicio;
        this.estadoContable = EstadoContableServicio.PENDIENTE_PROFORMA;
        this.numeroSolicitud = servicioSolicitud;
    }

    protected Servicio() {
        this.estadoContable = EstadoContableServicio.PENDIENTE_PROFORMA;
    }

    public EstadoServicio siguienteEstadoServicio() {
        EstadoServicio siguienteEstado = this.estado.getSiguienteEstado();
        if(siguienteEstado!=null){
            this.estado = siguienteEstado;
        }

        return this.estado;
    }

    public EstadoContableServicio siguienteEstadoContableServicio() {
        EstadoContableServicio siguienteEstado = this.estadoContable.getSiguienteEstado();
        if(siguienteEstado!=null){
            this.estadoContable = siguienteEstado;
        }

        return this.estadoContable;
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


