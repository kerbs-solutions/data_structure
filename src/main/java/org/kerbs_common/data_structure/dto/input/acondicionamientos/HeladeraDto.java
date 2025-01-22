package org.kerbs_common.data_structure.dto.input.acondicionamientos;

import org.kerbs_common.data_structure.dto.input.precios.PrecioDto;

public record HeladeraDto<TipoPrecio extends PrecioDto>(String empresa, int activo, String serie, String marca, String modelo, boolean enTaller,
                                                        String nroCliente, int nroSolicitud, String razonSocial,
                                                        String direccion, String localidad, AcondicionamientoDto<TipoPrecio> acondicionamientoDto) {
}