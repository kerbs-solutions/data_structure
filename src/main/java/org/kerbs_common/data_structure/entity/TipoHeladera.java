package org.kerbs_common.data_structure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;

@Entity
@Table(name = "tipo_heladera")
@Getter
public class TipoHeladera {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_heladera_seq")
    @SequenceGenerator(name = "tipo_heladera_seq", sequenceName = "tipo_heladera_id_seq", allocationSize = 1)
    @Column(name = "tipo_id")
    private Long id;

    @NonNull
    @Column(name = "tipo_descripcion", nullable = false, unique = true, length = 100)
    private String descripcion;

    @Column(name = "tipo_activo")
    private boolean activo;

    protected TipoHeladera() {
        this.activo = true;
    }

    public TipoHeladera(@NonNull String descripcion) {
        this.descripcion = descripcion;
        this.activo = true;
    }

    public TipoHeladera(@NonNull String descripcion, boolean activo) {
        this.descripcion = descripcion;
        this.activo = activo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoHeladera that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}