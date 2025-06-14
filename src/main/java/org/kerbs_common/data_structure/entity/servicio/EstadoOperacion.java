package org.kerbs_common.data_structure.entity.servicio;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EstadoOperacion {

    INFORMADA(null), PENDIENTE_INFORME(INFORMADA);

    private final EstadoOperacion siguienteEstado;
}