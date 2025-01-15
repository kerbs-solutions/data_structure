package org.kerbs.common.data_structure.entity.precio;


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

    public ManoDeObra(@NonNull String id, double valor, @NonNull String descripcion) {
        super(id, valor, descripcion);
    }


    @Override
    public double getPrecioTotal() {
        return this.getValor();
    }
}
