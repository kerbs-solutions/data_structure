package org.kerbs_common.data_structure.entity.precio;


import org.kerbs_common.data_structure.dto.input.precios.PrecioConValorDto;
import org.kerbs_common.data_structure.dto.input.precios.PrecioDto;
import org.kerbs_common.data_structure.dto.input.precios.PrecioSinValorDto;
import org.kerbs_common.data_structure.dto.output.proformas.AcondicionamientoProformaDto;
import org.kerbs_common.data_structure.dto.output.proformas.ReparacionProformaDto;
import org.kerbs_common.data_structure.exception.PrecioCodNotValidException;
import org.springframework.stereotype.Component;

@Component
public class PrecioFactory {

    public Precio getPrecio(PrecioConValorDto precioDto){

        Precio precio = null;
        //ensure using this inside the converter if previously (before using the converter, in the parent method the precios are validated bc you cannot throw exceptions inside a converter)
        if (isPrecioValid(precioDto)) {
            if (precioDto.getId().substring(0, 2).equalsIgnoreCase("MO")) {
                precio = new ManoDeObra(precioDto.getId(),precioDto.getPrecio_unitario(), precioDto.getDescripcion());
            } else if (precioDto.getId().substring(0, 1).equalsIgnoreCase("R")) {
                precio = new Repuesto(precioDto.getId(),precioDto.getPrecio_unitario(), precioDto.getDescripcion());
            }
        }

        return precio;

    }

    //todo: probably deprecate
    public boolean isPrecioValid(PrecioDto precioDto) {
        return precioDto.getId().substring(0, 2).equalsIgnoreCase("MO") || precioDto.getId().substring(0, 1).equalsIgnoreCase("R");
    }

    public boolean arePreciosValid(AcondicionamientoProformaDto acondicionamientoProformaDto) throws PrecioCodNotValidException {

        for (PrecioDto precioDto : acondicionamientoProformaDto.reparaciones().stream().map(ReparacionProformaDto::precioDto).toList()) {
            if (!this.isPrecioValid(precioDto)) {
                throw new PrecioCodNotValidException("Precio no válido. Por favor ingrese un codigo que comience por R o MO según el tipo que corresponda");
            }
        }

        return true;
    }
}
