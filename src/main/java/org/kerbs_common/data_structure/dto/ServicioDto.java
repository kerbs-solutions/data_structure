package org.kerbs_common.data_structure.dto;

import lombok.NonNull;
import org.kerbs_common.data_structure.dto.input.acondicionamientos.AcondicionamientoDto;
import org.kerbs_common.data_structure.dto.input.acondicionamientos.HeladeraDto;

import java.util.List;


public record ServicioDto(@NonNull HeladeraDto heladera, @NonNull List<AcondicionamientoDto> acondicionamientos) {
}
