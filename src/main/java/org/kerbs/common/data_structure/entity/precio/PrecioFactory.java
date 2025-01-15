//package org.kerbs.common.data_structure.entity.precio;
//
//
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class PrecioFactory {
//
//    public Precio getPrecio(PrecioDto precioDto) throws PrecioCodNotValidException {
//
//        Precio precio = null;
//        //ensure using this inside the converter if previously (before using the converter, in the parent method the precios are validated bc you cannot throw exceptions inside a converter)
//        if (isPrecioValid(precioDto)) {
//            if (precioDto.id().substring(0, 2).equalsIgnoreCase("MO")) {
//                precio = new ManoDeObra(precioDto.id(), precioDto.precio_unitario(), precioDto.descripcion());
//            } else if (precioDto.id().substring(0, 1).equalsIgnoreCase("R")) {
//                precio = new Repuesto(precioDto.id(), precioDto.precio_unitario(), precioDto.descripcion());
//            }
//        }
//
//        return precio;
//
//    }
//
//    public boolean isPrecioValid(PrecioDto precioDto) {
//        if (precioDto.id().substring(0, 2).equalsIgnoreCase("MO")) {
//            return true;
//        } else return precioDto.id().substring(0, 1).equalsIgnoreCase("R");
//
//    }
//
//    public boolean arePreciosValid(AcondicionamientoProformaDto acondicionamientoProformaDto) throws PrecioCodNotValidException {
//
//        for (PrecioDto precioDto : acondicionamientoProformaDto.reparaciones().stream().map(ReparacionProformaDto::precioDto).toList()) {
//            if (!this.isPrecioValid(precioDto)) {
//                throw new PrecioCodNotValidException("Precio no válido. Por favor ingrese un codigo que comience por R o MO según el tipo que corresponda");
//            }
//        }
//
//        return true;
//    }
//}
