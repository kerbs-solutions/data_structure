package org.kerbs_common.data_structure.dto.input.precios.converter;


import org.kerbs_common.data_structure.dto.input.precios.PrecioSinValorDto;
import org.kerbs_common.data_structure.entity.precio.Precio;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PrecioToPrecioSinValorDtoConverter implements Converter<Precio, PrecioSinValorDto>, Function<Precio, PrecioSinValorDto> {
    @Override
    public PrecioSinValorDto convert(Precio source) {
        return new PrecioSinValorDto(source.getId(), source.getDescripcion());
    }

    @Override
    public PrecioSinValorDto apply(Precio precio) {
        return convert(precio);
    }
}
