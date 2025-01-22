package org.kerbs_common.data_structure.dto.input.precios;

import lombok.Getter;
import lombok.NonNull;

@Getter
public non-sealed class PrecioSinValorDto extends PrecioDto{

    public PrecioSinValorDto(@NonNull String id, String descripcion) {
        super(id, descripcion);
    }
}
