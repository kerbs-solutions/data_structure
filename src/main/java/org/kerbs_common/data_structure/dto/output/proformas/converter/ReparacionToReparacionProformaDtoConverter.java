package org.kerbs_common.data_structure.dto.output.proformas.converter;

import org.kerbs_common.data_structure.dto.input.precios.PrecioConValorDto;
import org.kerbs_common.data_structure.dto.output.proformas.ReparacionProformaDto;
import org.kerbs_common.data_structure.entity.facturable.Reparacion;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ReparacionToReparacionProformaDtoConverter implements Converter<Reparacion, ReparacionProformaDto>, Function<Reparacion, ReparacionProformaDto> {
    @Override
    public ReparacionProformaDto convert(Reparacion source) {
        return new ReparacionProformaDto(source.getId(),new PrecioConValorDto(source.getPrecio().getId(),source.getPrecio().getValor(), source.getPrecio().getDescripcion()),source.getValorUnitarioReal(), source.getCantidad(), source.getPrecioTotal());
    }

    @Override
    public ReparacionProformaDto apply(Reparacion reparacion) {
        return convert(reparacion);
    }
}
