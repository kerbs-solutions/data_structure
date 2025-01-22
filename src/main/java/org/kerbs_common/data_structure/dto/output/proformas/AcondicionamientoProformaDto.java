package org.kerbs_common.data_structure.dto.output.proformas;


import lombok.NonNull;
import org.kerbs_common.data_structure.entity.servicio.EstadoContableServicio;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public record AcondicionamientoProformaDto(int id, int nro_solicitud, @NonNull String estado_servicio, @NonNull
EstadoContableServicio estado_contable_servicio,
                                           boolean confirmada,
                                           @NonNull LocalDate fecha_resolucion, double total,
                                           @NonNull HeladeraProformaDto heladera, @NonNull
                                           List<ReparacionProformaDto> reparaciones) {

    public AcondicionamientoProformaDto {
        validateReparaciones(id, reparaciones);
    }

    void validateReparaciones(int id, @NonNull List<ReparacionProformaDto> reparaciones) {

        List<List<ReparacionProformaDto>> duplicatedReparaciones = reparaciones.stream().collect(Collectors.groupingBy(reparacion -> reparacion.precioDto().getId())).values().stream().filter(list -> list.size() > 1).toList();

        if (!duplicatedReparaciones.isEmpty()) {
            throw new IllegalArgumentException("No se admiten precios (repuestos o mano de obra) duplicados. Si la cantidad utilizada de un repuesto o mano de obra es mayor a 1, modifique su campo cantidad");
        }
    }
}

