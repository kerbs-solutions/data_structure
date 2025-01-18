package org.kerbs_common.data_structure.entity.servicio;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EstadoServicio {

    REALIZADO(null),FALLIDO(null), EN_PROCESO(REALIZADO), PENDIENTE(EN_PROCESO);

    private final EstadoServicio siguienteEstado;
}
