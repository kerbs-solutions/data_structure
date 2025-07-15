package org.kerbs_common.data_structure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * ClienteFinal entity representing individual/final clients in the Kerbs system.
 * 
 * Final clients have:
 * - Always use STANDARD pricing (no custom contracts)
 * - Can be created by Operations service (no Admin approval required)
 * - Individual/small business clients
 * 
 * Extends Cliente with simplified structure - only difference is pricing strategy.
 */
@Entity
@Table(name = "cliente_final", schema = "kerbs_operations")
@Getter
@Setter
public class ClienteFinal extends Cliente {

    @Column(name = "dni", length = 20)
    private String dni;

    // Constructors
    public ClienteFinal() {
        super();
    }

    public ClienteFinal(String razonSocial) {
        super("STANDARD", razonSocial); // ClienteFinal always uses STANDARD pricing
    }

    public ClienteFinal(String razonSocial, String nombre, String apellido) {
        super("STANDARD", razonSocial, nombre, apellido);
    }

    public ClienteFinal(String razonSocial, String nombre, String apellido, String dni, String cuit, 
                       String condicionIva, String direccion, String telefono, String email) {
        super("STANDARD", razonSocial, nombre, apellido, cuit, condicionIva, direccion, telefono, email);
        this.dni = dni;
    }

    // Business methods specific to ClienteFinal
    @Override
    public boolean usaPreciosPersonalizados() {
        return false; // ClienteFinal always uses standard pricing
    }

    // toString for debugging
    @Override
    public String toString() {
        return "ClienteFinal{" +
                "id=" + getId() +
                ", pricingPlan='" + getPricingPlan() + '\'' +
                ", razonSocial='" + getRazonSocial() + '\'' +
                ", dni='" + dni + '\'' +
                '}';
    }
}