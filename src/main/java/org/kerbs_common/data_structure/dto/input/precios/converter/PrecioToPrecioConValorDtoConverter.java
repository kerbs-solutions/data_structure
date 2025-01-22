package org.kerbs_common.data_structure.dto.input.precios.converter;



import org.kerbs_common.data_structure.dto.input.precios.PrecioConValorDto;
import org.kerbs_common.data_structure.entity.precio.Precio;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PrecioToPrecioConValorDtoConverter implements Converter<Precio, PrecioConValorDto>, Function<Precio, PrecioConValorDto> {
    @Override
    public PrecioConValorDto convert(Precio source) {
        return new PrecioConValorDto(source.getId(), source.getValor(), source.getDescripcion());
    }

    @Override
    public PrecioConValorDto apply(Precio precio) {
        return convert(precio);
    }
}
