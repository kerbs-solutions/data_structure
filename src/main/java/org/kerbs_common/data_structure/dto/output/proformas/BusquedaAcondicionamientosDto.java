package org.kerbs_common.data_structure.dto.output.proformas;

import org.kerbs_common.data_structure.entity.servicio.EstadoContableServicio;

import java.time.LocalDate;

public record BusquedaAcondicionamientosDto(String empresa, EstadoContableServicio estado_contable,
                                            String modelo, String activo, String serie, Integer nro_orden, Integer nro_solicitud,
                                            LocalDate fecha_inicio, LocalDate fecha_fin) {
}
