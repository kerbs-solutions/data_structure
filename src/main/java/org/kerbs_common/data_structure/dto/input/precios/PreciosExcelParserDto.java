package org.kerbs_common.data_structure.dto.input.precios;

import lombok.NonNull;
import org.kerbs_common.data_structure.dto.input.excelparser.FailedExcelParserDto;
import org.kerbs_common.data_structure.entity.precio.Precio;

import java.util.List;

public record PreciosExcelParserDto(int cantidadExitos, int cantidadFallidos, @NonNull List<Precio> registrosExitosos,
                                    @NonNull List<FailedExcelParserDto> registrosFallidos) {
}