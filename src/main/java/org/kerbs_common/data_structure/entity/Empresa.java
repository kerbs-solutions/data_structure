package org.kerbs_common.data_structure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Empresa entity representing corporate clients in the Kerbs system.
 * 
 * Corporate clients have:
 * - Custom pricing contracts (managed by Admin microservice)
 * - Cannot be created by Operations service (require Admin approval)
 * - Business-to-business relationships
 * 
 * Extends Cliente with simplified structure - only difference is pricing strategy.
 */
@Entity
@Table(name = "empresa", schema = "kerbs_operations")
@Getter
@Setter
public class Empresa extends Cliente {

    // Constructors
    public Empresa() {
        super();
    }

    public Empresa(String pricingPlan, String razonSocial) {
        super(pricingPlan, razonSocial);
    }

    public Empresa(String pricingPlan, String razonSocial, String nombre, String apellido) {
        super(pricingPlan, razonSocial, nombre, apellido);
    }

    public Empresa(String pricingPlan, String razonSocial, String nombre, String apellido, String cuit, 
                   String condicionIva, String direccion, String telefono, String email) {
        super(pricingPlan, razonSocial, nombre, apellido, cuit, condicionIva, direccion, telefono, email);
    }

    // Business methods specific to Empresa
    @Override
    public boolean usaPreciosPersonalizados() {
        return true; // Empresas always use custom pricing
    }

    public boolean esCorporativo() {
        return true;
    }

    // toString for debugging
    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + getId() +
                ", pricingPlan='" + getPricingPlan() + '\'' +
                ", razonSocial='" + getRazonSocial() + '\'' +
                '}';
    }
}