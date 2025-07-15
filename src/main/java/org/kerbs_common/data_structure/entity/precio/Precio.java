package org.kerbs_common.data_structure.entity.precio;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.SQLRestriction;
import org.kerbs_common.data_structure.entity.Cliente;

import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "precio_tipo", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorOptions(force = false)
@SQLRestriction("activo = true") //automatic filtering
@Getter
public abstract class Precio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @NonNull
    @Column(name = "codigo")
    private String codigo;
    
    @NonNull
    @Setter
    @Column(name = "precio_plan")
    private String pricingPlan;

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

    @Setter
    @Column(name = "activo")
    private boolean activo;
    
    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;
    
    @Column(name = "updated_at") 
    private java.time.LocalDateTime updatedAt;

    public Precio(@NonNull String codigo, @NonNull String pricingPlan, double valor, @NonNull String descripcion) {
        this.codigo = codigo;
        this.pricingPlan = pricingPlan;
        this.valor = valor;
        this.descripcion = descripcion;
        this.activo = true;
        this.createdAt = java.time.LocalDateTime.now();
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public Precio(@NonNull String codigo, @NonNull String pricingPlan, double valor, @NonNull String descripcion, org.kerbs_common.data_structure.entity.TipoHeladera tipoHeladera) {
        this(codigo, pricingPlan, valor, descripcion);
        this.tipoHeladera = tipoHeladera;
    }

    public Precio(@NonNull String codigo, @NonNull String pricingPlan, double valor, @NonNull String descripcion, org.kerbs_common.data_structure.entity.TipoHeladera tipoHeladera, boolean activo) {
        this(codigo, pricingPlan, valor, descripcion, tipoHeladera);
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
        return Objects.hash(id);
    }
}