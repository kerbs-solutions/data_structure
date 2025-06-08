package org.kerbs_common.data_structure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.kerbs_common.data_structure.entity.facturable.Movimiento;
import org.kerbs_common.data_structure.entity.servicio.Acondicionamiento;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "heladera")
@Getter
@IdClass(HeladeraId.class)
public class Heladera {
    @Id
    @Column(name = "heladera_marca")
    private String marca;
    @Id
    @Column(name = "heladera_nro_serie")
    private String serie;


    @OneToMany(mappedBy = "heladera")
    List<Movimiento> movimientos;
    @OneToMany(mappedBy = "heladera", cascade = CascadeType.ALL)
    List<Acondicionamiento> acondicionamientos;
    @Column(name = "heladera_empresa")
    private String empresa;

    @Column(name = "heladera_activo")
    private String activo;

    @Column(name = "heladera_modelo")
    private String modelo;
    @Column(name = "heladera_cliente_numero")
    private String numeroCliente;

    //datos cliente actual
    /*
     * en el movimiento de ingreso es el cliente de origen,
     * en el movimiento egreso, es el cliente destino
     *
     * */
    @Column(name = "heladera_cliente_razon_social")
    private String razonSocial;
    @Column(name = "heladera_cliente_direccion")
    private String direccion;
    @Column(name = "heladera_cliente_localidad")
    private String localidad;
    @Column(name = "heladera_en_taller")
    private boolean enTaller;

    @ManyToOne
    @JoinColumn(name = "heladera_tipo_id")
    private TipoHeladera tipo;

    //todo: agregar datos de contacto al parsear terrand

    protected Heladera() {
    }

    public Heladera(String empresa, String serie, String activo, String marca, String modelo, String numeroCliente, String razonSocial, String direccion, String localidad, boolean enTaller) {
        this.empresa = empresa;
        this.serie = serie;
        this.activo = activo;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroCliente = numeroCliente;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.localidad = localidad;
        this.enTaller = enTaller;
        this.movimientos = new ArrayList<>();
        this.acondicionamientos = new ArrayList<>();
    }

    public Heladera(String empresa, String serie, String activo, String marca, String modelo, String numeroCliente, String razonSocial, String direccion, String localidad, boolean enTaller, TipoHeladera tipo) {
        this(empresa, serie, activo, marca, modelo, numeroCliente, razonSocial, direccion, localidad, enTaller);
        this.tipo = tipo;
    }

    public void addAcondcionamiento(Acondicionamiento acondicionamiento) {
        acondicionamiento.setHeladera(this);
        acondicionamientos.add(acondicionamiento);
    }


}
