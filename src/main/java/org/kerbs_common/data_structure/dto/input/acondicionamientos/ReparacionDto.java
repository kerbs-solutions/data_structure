package org.kerbs_common.data_structure.dto.input.acondicionamientos;

import org.kerbs_common.data_structure.dto.input.precios.PrecioDto;

public record ReparacionDto<TipoPrecio extends PrecioDto>(TipoPrecio precio, double valor_unitario_real, double cantidad) {
}
