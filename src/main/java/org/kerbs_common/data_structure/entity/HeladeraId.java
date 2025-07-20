package org.kerbs_common.data_structure.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class HeladeraId implements Serializable {
    /** Heladera brand/make */
    private String marca;
    /** Heladera serial number */
    private String serie;

    // Constructor por defecto
    protected HeladeraId() {}

    // Constructor con argumentos
    public HeladeraId(String marca, String serie) {
        this.marca = marca;
        this.serie = serie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HeladeraId that)) return false;
        return Objects.equals(marca, that.marca) && Objects.equals(serie, that.serie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marca, serie);
    }
}
