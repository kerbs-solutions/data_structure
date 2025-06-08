package org.kerbs_common.data_structure.entity.precio;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.DiscriminatorOptions;

import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "precio_tipo", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorOptions(force = false)
@Getter
public abstract class Precio {
    @Id
    @NonNull
    @Column(name="precio_id")
    private String id;

    @Setter
    @Column(name="precio_valor")
    private double valor;
    @NonNull
    @Setter
    @Column(name="precio_descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "precio_tipo_heladera_id")
    private org.kerbs_common.data_structure.entity.TipoHeladera tipoHeladera;

    @Column(name = "precio_activo")
    private boolean activo;

    public Precio(@NonNull String id, double valor, @NonNull String descripcion) {
        this.id = id;
        this.valor = valor;
        this.descripcion = descripcion;
        this.activo = true;
    }

    public Precio(@NonNull String id, double valor, @NonNull String descripcion, org.kerbs_common.data_structure.entity.TipoHeladera tipoHeladera) {
        this(id, valor, descripcion);
        this.tipoHeladera = tipoHeladera;
    }

    public Precio(@NonNull String id, double valor, @NonNull String descripcion, org.kerbs_common.data_structure.entity.TipoHeladera tipoHeladera, boolean activo) {
        this(id, valor, descripcion, tipoHeladera);
        this.activo = activo;
    }

    protected Precio() {
        this.activo = true;
    }

    abstract double getPrecioTotal();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Precio precio)) return false;
        return Objects.equals(id, precio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}