package org.kerbs.common.data_structure.entity.facturable;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@MappedSuperclass
@Getter
public abstract class Facturable {

    @Column(name = "facturable_valor_unitario_real")
    //este es el valor que se le asignó al facturable al momento de realizarse la transaccion
    //puede ser el valor al momento de realizarlo o un valor provisto por el usuario
    //es el que se debe tomar en cuenta para calcular el precio total de un servicio
    //el precio es solo una referencia al inventario a un precio general
    @Setter
    protected double valoruUnitarioReal;

    abstract double getPrecioTotal();

}


