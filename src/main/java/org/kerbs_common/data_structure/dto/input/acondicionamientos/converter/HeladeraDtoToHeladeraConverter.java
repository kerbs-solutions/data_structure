package org.kerbs_common.data_structure.dto.input.acondicionamientos.converter;

import lombok.RequiredArgsConstructor;
import org.kerbs_common.data_structure.dto.input.acondicionamientos.HeladeraDto;
import org.kerbs_common.data_structure.dto.input.precios.PrecioConValorDto;
import org.kerbs_common.data_structure.dto.input.precios.PrecioDto;
import org.kerbs_common.data_structure.dto.input.precios.PrecioSinValorDto;
import org.kerbs_common.data_structure.entity.Heladera;
import org.kerbs_common.data_structure.entity.facturable.Reparacion;
import org.kerbs_common.data_structure.entity.precio.Precio;
import org.kerbs_common.data_structure.entity.precio.PrecioFactory;
import org.kerbs_common.data_structure.entity.servicio.Acondicionamiento;
import org.kerbs_common.data_structure.entity.servicio.EstadoServicio;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class HeladeraDtoToHeladeraConverter implements Converter<HeladeraDto<PrecioConValorDto>, Heladera>, Function<HeladeraDto<PrecioConValorDto>, Heladera> {

    private final PrecioFactory precioFactory;

    @Override
    public Heladera apply(HeladeraDto<PrecioConValorDto> heladeraDto) {
        return this.convert(heladeraDto);
    }

    @Override
    public Heladera convert(HeladeraDto<PrecioConValorDto> source) {
        Heladera heladera = new Heladera(source.empresa(), source.serie(), String.valueOf(source.activo()), source.marca(), source.modelo(), source.nroCliente(), source.razonSocial(), source.direccion(), source.localidad(), source.enTaller());


        List<Reparacion> reparaciones = source.acondicionamientoDto().reparaciones().stream()
                .map(reparacionDto -> {

                    return new Reparacion(precioFactory.getPrecio(reparacionDto.precio()),reparacionDto.valor_unitario_real(), reparacionDto.cantidad()); //cuando se modifica el precio y/o se arman las prformas

                }).toList();

        //todo check
        Acondicionamiento acondicionamiento = new Acondicionamiento(source.acondicionamientoDto().codAcondicionamiento(), source.nroSolicitud(),EstadoServicio.REALIZADO, null, null, source.acondicionamientoDto().fechaIngreso(), null);
        reparaciones.forEach(acondicionamiento::addReparacion);

        heladera.addAcondcionamiento(acondicionamiento);

        return heladera;
    }

}
