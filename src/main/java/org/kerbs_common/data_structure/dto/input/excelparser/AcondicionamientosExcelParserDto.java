package org.kerbs_common.data_structure.dto.input.excelparser;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.kerbs_common.data_structure.dto.input.acondicionamientos.HeladeraDto;
import org.kerbs_common.data_structure.dto.input.precios.PrecioConValorDto;
import org.kerbs_common.data_structure.dto.input.precios.PrecioSinValorDto;
import org.springframework.http.HttpStatus;

import java.util.List;

@Setter
@Getter
public class AcondicionamientosExcelParserDto{

    private String mensajePersistencia;
    private HttpStatus httpStatus;
    private int cantidadExitosLectura;
    private int cantidadFallidosLectura;
    @NonNull
    private List<HeladeraDto<PrecioConValorDto>> registrosExitososLectura;
    @NonNull
    private List<FailedExcelParserDto> registrosFallidosLectura;


    public AcondicionamientosExcelParserDto(int cantidadExitosLectura, int cantidadFallidosLectura, @NonNull List<HeladeraDto<PrecioConValorDto>> registrosExitososLectura, @NonNull List<FailedExcelParserDto> registrosFallidosLectura) {
        this.cantidadExitosLectura = cantidadExitosLectura;
        this.cantidadFallidosLectura = cantidadFallidosLectura;
        this.registrosExitososLectura = registrosExitososLectura;
        this.registrosFallidosLectura = registrosFallidosLectura;
    }

    public AcondicionamientosExcelParserDto(HttpStatus httpStatus, int cantidadExitosLectura, int cantidadFallidosLectura, @NonNull List<HeladeraDto<PrecioConValorDto>> registrosExitososLectura, @NonNull List<FailedExcelParserDto> registrosFallidosLectura) {
        this.httpStatus = httpStatus;
        this.cantidadExitosLectura = cantidadExitosLectura;
        this.cantidadFallidosLectura = cantidadFallidosLectura;
        this.registrosExitososLectura = registrosExitososLectura;
        this.registrosFallidosLectura = registrosFallidosLectura;
    }
}