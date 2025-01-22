package org.kerbs_common.data_structure.entity.facturable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.kerbs_common.data_structure.entity.precio.Precio;
import org.kerbs_common.data_structure.entity.servicio.Acondicionamiento;

import java.util.Objects;

@Entity
@Table(name = "reparacion")
@Getter
@Setter
@DynamicUpdate
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
        this.valorUnitarioReal = precio.getValor();
    }

    public Reparacion(@NonNull Precio precio, double valorUnirarioReal, double cantidad) {
        this.precio = precio;
        this.cantidad = cantidad;
        this.valorUnitarioReal = valorUnirarioReal;
    }

    public Reparacion(int id, @NonNull Precio precio, double valorUnirarioReal, double cantidad) {
        this.id = id;
        this.precio = precio;
        this.cantidad = cantidad;
        this.valorUnitarioReal = valorUnirarioReal;
    }


    protected Reparacion() {

    }


    @Override
    public double getPrecioTotal() {
        return this.getValorUnitarioReal() * cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reparacion that)) return false;
      //  return id == that.id && Objects.equals(acondicionamiento, that.acondicionamiento);
        return id == that.id;
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

    public void update(@NonNull Reparacion reparacionUpdated) {
        this.precio = reparacionUpdated.getPrecio();
        this.cantidad = reparacionUpdated.getCantidad();
        this.valorUnitarioReal = reparacionUpdated.getValorUnitarioReal();
    }
}
