package org.kerbs_common.data_structure.entity.facturable;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;


@MappedSuperclass
@Getter
public abstract class Facturable {


    abstract double getPrecioTotal();

}


