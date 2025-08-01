package org.kerbs_common.data_structure.entity.precio;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("REPUESTO")
public class Repuesto extends Precio {

    protected Repuesto() {
    }

    public Repuesto(@NonNull String id, @NonNull String pricingPlan, double precio, @NonNull String descripcion) {
        super(id, pricingPlan, precio, descripcion);
    }
    
    // Legacy constructor for backward compatibility during migration - uses STANDARD pricing
    @Deprecated
    public Repuesto(@NonNull String id, double precio, @NonNull String descripcion) {
        super(id, "STANDARD", precio, descripcion); // Default to STANDARD pricing plan
    }

    @Override
    public double getPrecioTotal() {
        return this.getValor();
    }

    //todo: cantidad para manejar el inventario
    //todo: costo para manejar el inventario
}
