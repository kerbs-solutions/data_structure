package org.kerbs_common.data_structure.dto.output.proformas.converter;

import lombok.RequiredArgsConstructor;
import org.kerbs_common.data_structure.dto.output.proformas.ReparacionProformaDto;
import org.kerbs_common.data_structure.entity.facturable.Reparacion;
import org.kerbs_common.data_structure.entity.precio.PrecioFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ReparacionProformaDtoConverterToReparacion implements Converter<ReparacionProformaDto, Reparacion>, Function<ReparacionProformaDto, Reparacion> {

    private final PrecioFactory precioFactory;
    @Override
    public Reparacion convert(ReparacionProformaDto source) {
        return new Reparacion(source.id_reparacion(),precioFactory.getPrecio(source.precioDto()),source.valor_unitario_real(),source.cantidad());
    }

    @Override
    public Reparacion apply(ReparacionProformaDto reparacion) {
        return convert(reparacion);
    }
}
