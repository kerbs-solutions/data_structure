package org.kerbs_common.data_structure.dto.input.precios;

import lombok.Getter;
import lombok.NonNull;

@Getter
public abstract sealed class PrecioDto permits PrecioConValorDto, PrecioSinValorDto{
    private @NonNull String id;
    private String descripcion;

    public PrecioDto(@NonNull String id) {
        this.id = id;
    }
    public PrecioDto(@NonNull String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    private PrecioDto() {

    }
}
