package org.kerbs_common.data_structure.dto.output.proformas;

import lombok.NonNull;

import java.util.List;

public record AcondicionamientoPaginatedDto(@NonNull List<AcondicionamientoProformaDto> acondicionamientos, Long totalAmount) {
}
