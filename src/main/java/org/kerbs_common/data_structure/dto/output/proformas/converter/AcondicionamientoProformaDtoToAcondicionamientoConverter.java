package org.kerbs_common.data_structure.dto.output.proformas.converter;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.kerbs_common.data_structure.dto.input.precios.PrecioSinValorDto;
import org.kerbs_common.data_structure.dto.output.proformas.AcondicionamientoProformaDto;
import org.kerbs_common.data_structure.dto.output.proformas.ReparacionProformaDto;
import org.kerbs_common.data_structure.entity.facturable.Reparacion;
import org.kerbs_common.data_structure.entity.precio.Precio;
import org.kerbs_common.data_structure.entity.precio.PrecioFactory;
import org.kerbs_common.data_structure.entity.servicio.Acondicionamiento;
import org.kerbs_common.data_structure.exception.PrecioCodNotValidException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class AcondicionamientoProformaDtoToAcondicionamientoConverter implements Converter<AcondicionamientoProformaDto, Acondicionamiento>, Function<AcondicionamientoProformaDto, Acondicionamiento> {

    private final PrecioFactory precioFactory;

    @Override
    public Acondicionamiento convert(AcondicionamientoProformaDto source) {

        //todo manejar error de no encontrar el codigo del precio!!!

        List<Reparacion> reparaciones = new ArrayList<>();
        for (val dto : source.reparaciones()) {

            Reparacion reparacion = null;
            Precio precio = null;

            //validacion existencia de precio --> refactor
            precio = precioFactory.getPrecio(dto.precioDto());

            reparacion = new Reparacion(dto.id_reparacion(), precio, dto.valor_unitario_real(), dto.cantidad());

            reparaciones.add(reparacion);
        }


        return new Acondicionamiento(source.id(), reparaciones);
    }

    @Override
    public Acondicionamiento apply(AcondicionamientoProformaDto dto) {
        return convert(dto);
    }
}
