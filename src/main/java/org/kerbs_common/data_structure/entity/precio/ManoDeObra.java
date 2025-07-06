package org.kerbs_common.data_structure.entity.precio;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("MANO DE OBRA")
public class ManoDeObra extends Precio {

    protected ManoDeObra() {
    }

    public ManoDeObra(@NonNull String id, @NonNull String pricingPlan, double valor, @NonNull String descripcion) {
        super(id, pricingPlan, valor, descripcion);
    }
    
    // Legacy constructor for backward compatibility during migration - uses STANDARD pricing
    @Deprecated
    public ManoDeObra(@NonNull String id, double valor, @NonNull String descripcion) {
        super(id, "STANDARD", valor, descripcion); // Default to STANDARD pricing plan
    }

    @Override
    public double getPrecioTotal() {
        return this.getValor();
    }
}
