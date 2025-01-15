package org.kerbs.common.data_structure.entity.servicio;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EstadoContableServicio {

    COBRADA(null),FACTURADA(COBRADA), PENDIENTE_FACTURACION(FACTURADA),PENDIENTE_NOTIFICACION_CLIENTE(PENDIENTE_FACTURACION), PENDIENTE_PROFORMA(PENDIENTE_NOTIFICACION_CLIENTE);

    private final EstadoContableServicio siguienteEstado;
}
