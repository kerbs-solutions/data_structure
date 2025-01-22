package org.kerbs_common.data_structure.dto.output.proformas;

import lombok.NonNull;

public record HeladeraProformaDto(@NonNull String empresa, @NonNull String modelo, @NonNull String activo_serie) {
}
