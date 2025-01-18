package org.kerbs_common.data_structure.entity.servicio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.NonNull;
import org.kerbs_common.data_structure.entity.facturable.Movimiento;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "viaje")
public class Viaje extends Servicio{


    @Column(name = "viaje_fecha_resolucion")
    private LocalDate fechaResolucion;

    @OneToMany(mappedBy = "viaje")
    private List<Movimiento> movimientos;


    public Viaje(@NonNull EstadoServicio estadoServicio, LocalDate fechaResolucion, List<Movimiento> movimientos) {
        super(estadoServicio);
        this.fechaResolucion = fechaResolucion;
        this.movimientos = movimientos;
    }

    protected Viaje() {
    }

    //todo: analizar cant de heladeras y si hay que cobrar por 3
    @Override
    public double getPrecioTotal() {

        //for each movimiento --> sumatoria de getPrecioTotal y considerar si hay que cobrar x3
        return 0;
    }
}
