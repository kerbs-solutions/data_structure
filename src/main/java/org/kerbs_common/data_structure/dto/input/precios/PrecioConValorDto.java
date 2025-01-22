package org.kerbs_common.data_structure.dto.input.precios;

import lombok.Getter;
import lombok.NonNull;

@Getter
public non-sealed class PrecioConValorDto extends PrecioDto {

    private double precio_unitario;
    public PrecioConValorDto(@NonNull String id, double precio_unitario, String descripcion){
        super(id, descripcion);
        this.precio_unitario = precio_unitario;
    }
}
