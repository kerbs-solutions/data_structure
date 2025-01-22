package org.kerbs_common.data_structure.dto.output.proformas;

import lombok.NonNull;
import org.kerbs_common.data_structure.dto.input.precios.PrecioConValorDto;

public record ReparacionProformaDto(int id_reparacion, @NonNull PrecioConValorDto precioDto, double valor_unitario_real, double cantidad, double precio_total) {
}
