package org.kerbs.common.data_structure.entity.facturable;


import com.kerbs.invoicingservice.entity.precio.Precio;
import com.kerbs.invoicingservice.entity.servicio.Acondicionamiento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "reparacion")
@Getter
@Setter
public class Reparacion extends Facturable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reparacion_id")
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE) //a new precio can be created from this association but deleting a reparacion does not delete existing precios
    //precio is only an inventary reference, not a real price, real price is set in field valorReal
    @JoinColumn(name = "reparacion_precio_id")
    private Precio precio;

    @ManyToOne
    @JoinColumn(name = "reparacion_acondicionamiento", referencedColumnName = "servicio_id")
    @Setter
    private Acondicionamiento acondicionamiento;

    @Column(name = "reparacion_cantidad")
    private double cantidad;

    public Reparacion(@NonNull Precio precio, double cantidad) {
        this.precio = precio;
        this.cantidad = cantidad;
        this.valoruUnitarioReal = precio.getValor();
    }

    public Reparacion(@NonNull Precio precio, double valor, double cantidad) {
        this.precio = precio;
        this.cantidad = cantidad;
        this.valoruUnitarioReal = valor;
    }

    public Reparacion(int id, @NonNull Precio precio, double valor, double cantidad) {
        this.id = id;
        this.precio = precio;
        this.cantidad = cantidad;
        this.valoruUnitarioReal = valor;
    }


    protected Reparacion() {

    }


    @Override
    public double getPrecioTotal() {
        return this.getValoruUnitarioReal() * cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reparacion that)) return false;
        return id == that.id && Objects.equals(acondicionamiento, that.acondicionamiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, acondicionamiento);
    }


    private boolean isPrecioNull() {
        return this.precio == null;
    }

    public boolean isInvalid() {
        return isPrecioNull() || !(this.precio.getId().startsWith("MO") || this.precio.getId().startsWith("R"));
    }
}
