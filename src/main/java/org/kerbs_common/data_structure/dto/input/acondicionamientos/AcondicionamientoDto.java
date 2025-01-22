package org.kerbs_common.data_structure.dto.input.acondicionamientos;

import lombok.NonNull;
import org.kerbs_common.data_structure.dto.input.precios.PrecioDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record AcondicionamientoDto<TipoPrecio extends PrecioDto>(int codAcondicionamiento, LocalDate fechaIngreso, List<ReparacionDto<TipoPrecio>> reparaciones) {
    public AcondicionamientoDto {
        validateReparaciones(reparaciones);
    }

    void validateReparaciones(@NonNull List<ReparacionDto<TipoPrecio>> reparaciones) {

        List<List<ReparacionDto<TipoPrecio>>> duplicatedReparaciones = reparaciones.stream().collect(Collectors.groupingBy(reparacion -> reparacion.precio().getId())).values().stream().filter(list -> list.size() > 1).toList();

        if (!duplicatedReparaciones.isEmpty()) {
            throw new IllegalArgumentException("No se admiten precios (repuestos o mano de obra) duplicados. Si la cantidad utilizada de un repuesto o mano de obra es mayor a 1, modifique su campo cantidad");
        }
    }

}
