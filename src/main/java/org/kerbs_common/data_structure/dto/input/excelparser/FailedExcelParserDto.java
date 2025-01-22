package org.kerbs_common.data_structure.dto.input.excelparser;

import lombok.NonNull;

public record FailedExcelParserDto(@NonNull String id, @NonNull String failureMessage){}