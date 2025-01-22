package org.kerbs_common.data_structure.dto.output.proformas.converter;


import lombok.RequiredArgsConstructor;
import org.kerbs_common.data_structure.dto.output.proformas.AcondicionamientoProformaDto;
import org.kerbs_common.data_structure.dto.output.proformas.HeladeraProformaDto;
import org.kerbs_common.data_structure.entity.servicio.Acondicionamiento;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class AcondicionamientoToAcondicionamientoProformaDtoConverter implements Converter<Acondicionamiento, AcondicionamientoProformaDto>, Function<Acondicionamiento, AcondicionamientoProformaDto> {

    private final ReparacionToReparacionProformaDtoConverter reparacionToReparacionProformaDtoConverter;

    @Override
    public AcondicionamientoProformaDto convert(Acondicionamiento source) {
        return new AcondicionamientoProformaDto(source.getId(), source.getNumeroSolicitud(), source.getEstado().name(), source.getEstadoContable(),source.confirmada(), source.getFechaResolucion(), source.getPrecioTotal(),
                new HeladeraProformaDto(source.getHeladera().getEmpresa(), source.getHeladera().getModelo(), source.getHeladera().getActivo() + "/" + source.getHeladera().getSerie()),
                source.getReparaciones().stream().map(reparacionToReparacionProformaDtoConverter).toList()
                );
    }

    @Override
    public AcondicionamientoProformaDto apply(Acondicionamiento acondicionamiento) {
        return convert(acondicionamiento);
    }
}
