package org.kerbs.common.data_structure.entity.servicio;

import com.kerbs.invoicingservice.entity.Heladera;
import com.kerbs.invoicingservice.entity.facturable.Reparacion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "acondicionamiento")
@Getter
public class Acondicionamiento extends Servicio {

    @Column(name = "acond_fecha_asignacion")
    private LocalDate fechaAsignacion;
    @Column(name = "acond_fecha_ingreso")
    private LocalDate fechaIngreso; //analizar movimiento
    @Column(name = "acond_fecha_resolucion")
    private LocalDate fechaResolucion;
    @Column(name = "acond_fecha_salida")
    private LocalDate fechaSalida; //analizar movimiento


    @OneToMany(mappedBy = "acondicionamiento",cascade=CascadeType.ALL)
    private List<Reparacion> reparaciones;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "acond_heladera_marca", referencedColumnName = "heladera_marca"),
            @JoinColumn(name = "acond_heladera_serie", referencedColumnName = "heladera_nro_serie")
    })
    @Setter
    private Heladera heladera;


    public Acondicionamiento(int id, int numeroSolicitud, @NonNull EstadoServicio estadoServicio, LocalDate fechaAsignacion, LocalDate fechaIngreso, LocalDate fechaResolucion, LocalDate fechaSalida) {
        super(id,estadoServicio,numeroSolicitud);
        this.fechaAsignacion = fechaAsignacion;
        this.fechaIngreso = fechaIngreso;
        this.fechaResolucion = fechaResolucion;
        this.fechaSalida = fechaSalida;
        this.reparaciones = new ArrayList<>();
    }

    public Acondicionamiento(int id, @NonNull List<Reparacion> reparaciones){
        super(id);
        this.reparaciones = reparaciones;
    }

    public void addReparacion(Reparacion reparacion) {
        reparacion.setAcondicionamiento(this);
        this.reparaciones.add(reparacion);
    }

    protected Acondicionamiento() {
    }


    public void updateReparaciones(List<Reparacion>reparaciones){

        reparaciones.forEach(reparacion -> reparacion.setAcondicionamiento(this));
        this.reparaciones.clear();
        this.reparaciones.addAll(reparaciones);
    }


    @Override
    public double getPrecioTotal() {
        return this.reparaciones.stream().map(Reparacion::getPrecioTotal).reduce(0.0, Double::sum);
    }

    public boolean confirmada(){
        return !this.getEstadoContable().equals(EstadoContableServicio.PENDIENTE_PROFORMA);
    }




}
