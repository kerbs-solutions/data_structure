package org.kerbs_common.data_structure.entity.facturable;

import jakarta.persistence.*;
import lombok.NonNull;
import org.kerbs_common.data_structure.entity.Heladera;
import org.kerbs_common.data_structure.entity.precio.Precio;
import org.kerbs_common.data_structure.entity.servicio.Viaje;

@Entity
@Table(name = "movimiento")
public class Movimiento extends Facturable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movimiento_id")
    private int id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "movimiento_heladera_marca", referencedColumnName = "heladera_marca"),
            @JoinColumn(name = "movimiento_heladera_serie", referencedColumnName = "heladera_nro_serie")
    })
    @NonNull
    private Heladera heladera; //si es salida, el cliente se actualiza a destino, si es entrada, el cliente es el de origen

    @ManyToOne
    @JoinColumn(name = "movimiento_inventario")
    @NonNull
    private Precio precio;

    @Column(name = "movimiento_km_excedente")
    private double kmExcedente;

    @ManyToOne
    @JoinColumn(name = "movimiento_viaje", referencedColumnName = "servicio_id")
    @NonNull
    private Viaje viaje;

    public Movimiento(int id,@NonNull Heladera heladera, @NonNull Precio precio, double valor, double kmExcedente, @NonNull Viaje viaje) {
        this.id = id;
        this.heladera = heladera;
        this.precio = precio;
        this.kmExcedente = kmExcedente;
        this.viaje = viaje;
        this.valorUnitarioReal = valor;
    }

    public Movimiento(@NonNull Heladera heladera, @NonNull Precio precio, double valor, double kmExcedente, @NonNull Viaje viaje) {
        this.heladera = heladera;
        this.precio = precio;
        this.kmExcedente = kmExcedente;
        this.viaje = viaje;
        this.valorUnitarioReal = valor;
    }

    public Movimiento(@NonNull Heladera heladera, @NonNull Precio precio,double kmExcedente, @NonNull Viaje viaje) {
        this.heladera = heladera;
        this.precio = precio;
        this.kmExcedente = kmExcedente;
        this.viaje = viaje;
        this.valorUnitarioReal = this.precio.getValor();
    }


    protected Movimiento() {
    }

    @Override
    public double getPrecioTotal() {
        //todo: considerar km excedente
        return this.getValorUnitarioReal();
    }
}
