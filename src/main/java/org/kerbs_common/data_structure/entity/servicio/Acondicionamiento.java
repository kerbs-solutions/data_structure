package org.kerbs_common.data_structure.entity.servicio;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.kerbs_common.data_structure.entity.Heladera;
import org.kerbs_common.data_structure.entity.facturable.Reparacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "acondicionamiento")
@Getter
public class Acondicionamiento extends Servicio {

    @Column(name = "acond_fecha_asignacion")
    @Setter
    private LocalDate fechaAsignacion;
    @Column(name = "acond_fecha_ingreso")
    @Setter
    private LocalDate fechaIngreso; //analizar movimiento
    @Column(name = "acond_fecha_resolucion")
    @Setter
    private LocalDate fechaResolucion;
    @Column(name = "acond_fecha_salida")
    @Setter
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

    @Column(name = "acond_tecnico")
    @Setter
    private String tecnico;

    @Column(name = "acond_observaciones")
    @Setter
    private String observaciones;


    public Acondicionamiento(int id, int numeroSolicitud, @NonNull EstadoServicio estadoServicio, LocalDate fechaAsignacion, LocalDate fechaIngreso, LocalDate fechaResolucion, LocalDate fechaSalida) {
        super(id,estadoServicio,numeroSolicitud);
        this.fechaAsignacion = fechaAsignacion;
        this.fechaIngreso = fechaIngreso;
        this.fechaResolucion = fechaResolucion;
        this.fechaSalida = fechaSalida;
        this.reparaciones = new ArrayList<>();
    }

    public Acondicionamiento(int id, int numeroSolicitud, @NonNull EstadoServicio estadoServicio, LocalDate fechaAsignacion, LocalDate fechaIngreso, LocalDate fechaResolucion, LocalDate fechaSalida, String tecnico, String observaciones) {
        super(id,estadoServicio,numeroSolicitud);
        this.fechaAsignacion = fechaAsignacion;
        this.fechaIngreso = fechaIngreso;
        this.fechaResolucion = fechaResolucion;
        this.fechaSalida = fechaSalida;
        this.tecnico = tecnico;
        this.observaciones = observaciones;
        this.reparaciones = new ArrayList<>();
    }

    public Acondicionamiento(int id, @NonNull List<Reparacion> reparaciones){
        super(id);
        this.reparaciones = reparaciones;
    }

    /**
     * Constructor for new Acondicionamiento without ID (for creation with DB auto-increment).
     * Uses default states and initializes reparaciones collection.
     */
    public Acondicionamiento(@NonNull EstadoServicio estadoServicio, LocalDate fechaAsignacion, String tecnico, String observaciones) {
        super(estadoServicio);
        this.fechaAsignacion = fechaAsignacion;
        this.tecnico = tecnico;
        this.observaciones = observaciones;
        this.reparaciones = new ArrayList<>();
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

    public void smartUpdateReparaciones(List<Reparacion> newReparaciones) {
        // Create maps for efficient lookup
        var existingById = this.reparaciones.stream()
            .collect(java.util.stream.Collectors.toMap(Reparacion::getId, r -> r));
        
        var newById = newReparaciones.stream()
            .filter(r -> r.getId() != 0) // Only include reparaciones with existing IDs
            .collect(java.util.stream.Collectors.toMap(Reparacion::getId, r -> r));

        // 1. Update existing reparaciones
        for (Reparacion existing : this.reparaciones) {
            if (newById.containsKey(existing.getId())) {
                Reparacion updated = newById.get(existing.getId());
                existing.setPrecio(updated.getPrecio());
                existing.setCantidad(updated.getCantidad());
                existing.setObservaciones(updated.getObservaciones());
            }
        }

        // 2. Remove reparaciones that are no longer in the update list
        this.reparaciones.removeIf(existing -> !newById.containsKey(existing.getId()));

        // 3. Add new reparaciones (those with ID = 0 or not in existing)
        for (Reparacion newRep : newReparaciones) {
            if (newRep.getId() == 0 || !existingById.containsKey(newRep.getId())) {
                newRep.setAcondicionamiento(this);
                this.reparaciones.add(newRep);
            }
        }
    }


    @Override
    public double getPrecioTotal() {
        return this.reparaciones.stream().map(Reparacion::getPrecioTotal).reduce(0.0, Double::sum);
    }

    public boolean confirmada(){
        return !this.getEstadoContable().equals(EstadoContableServicio.PENDIENTE_PROFORMA);
    }




}
