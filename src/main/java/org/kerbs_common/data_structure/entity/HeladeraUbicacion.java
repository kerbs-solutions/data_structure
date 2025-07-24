package org.kerbs_common.data_structure.entity;

import jakarta.persistence.*;


import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * S047: HeladeraUbicacion entity for tracking heladera placement history
 * with legal documentation and client location information.
 * 
 * Separates "heladera owner" (our client who owns the equipment) from 
 * "current cliente" (where the heladera is physically placed).
 * 
 * This entity tracks the complete lifecycle of heladera placements with:
 * - Legal documentation (comodato numbers)
 * - Client information (where placed, not the owner)
 * - Date tracking for placement history
 * - Status management for active/inactive placements
 */
@Entity
@Table(name = "heladera_ubicacion", schema = "kerbs_operations")
public class HeladeraUbicacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ubicacion_id")
    private Long id;
    
    /**
     * Reference to heladera using stable internal ID (S008)
     * This FK is immune to equipment part changes (marca/serie modifications)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "heladera_internal_id", referencedColumnName = "heladera_internal_id", nullable = false)
    private Heladera heladera;
    
    /**
     * Legal document number for heladera placement contract
     * Format: COM-YYYY-NNN or TALLER-YYYY for workshop placements
     * Can be null when there's no formal comodato contract
     */
    @Column(name = "numero_comodato", nullable = true)
    private String numeroComodato;
    
    /**
     * Client number code used by our clients to identify their clients
     * Can be null if no specific client number is assigned
     */
    @Column(name = "numero_cliente", nullable = true)
    private String numeroCliente;
    
    /**
     * End-client company name where heladera is physically placed
     * This is NOT the heladera owner (which is stored in heladera.cliente)
     */
    @Column(name = "cliente_razon_social", nullable = false)
    private String clienteRazonSocial;
    
    /**
     * Physical address where heladera is located
     */
    @Column(name = "heladera_ubicacion_direccion", nullable = false)
    private String clienteDireccion;
    
    /**
     * City/locality where heladera is located
     */
    @Column(name = "heladera_ubicacion_localidad", nullable = false)
    private String clienteLocalidad;
    
    /**
     * Date when heladera arrived at this client location
     */
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;
    
    /**
     * Date when heladera was moved from this location
     * NULL indicates this is the current placement
     */
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    
    /**
     * Placement status: ACTIVO for current placement, INACTIVO for historical
     * Business rule: Only one ACTIVO placement per heladera (enforced by unique index)
     */
    @Column(name = "estado", nullable = false)
    private String estado = "ACTIVO";
    
    /**
     * Audit fields for tracking record creation and updates
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Default constructor for JPA
    public HeladeraUbicacion() {
    }
    
    /**
     * Business constructor for creating new heladera placements
     */
    public HeladeraUbicacion(Heladera heladera, String numeroComodato, String numeroCliente,
                           String clienteRazonSocial, String clienteDireccion, 
                           String clienteLocalidad, LocalDate fechaInicio) {
        this.heladera = heladera;
        this.numeroComodato = numeroComodato;
        this.numeroCliente = numeroCliente;
        this.clienteRazonSocial = clienteRazonSocial;
        this.clienteDireccion = clienteDireccion;
        this.clienteLocalidad = clienteLocalidad;
        this.fechaInicio = fechaInicio;
        this.estado = "ACTIVO";
    }
    
    /**
     * Set audit timestamps before persistence operations
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Heladera getHeladera() {
        return heladera;
    }
    
    public void setHeladera(Heladera heladera) {
        this.heladera = heladera;
    }
    
    public String getNumeroComodato() {
        return numeroComodato;
    }
    
    public void setNumeroComodato(String numeroComodato) {
        this.numeroComodato = numeroComodato;
    }
    
    public String getNumeroCliente() {
        return numeroCliente;
    }
    
    public void setNumeroCliente(String numeroCliente) {
        this.numeroCliente = numeroCliente;
    }
    
    public String getClienteRazonSocial() {
        return clienteRazonSocial;
    }
    
    public void setClienteRazonSocial(String clienteRazonSocial) {
        this.clienteRazonSocial = clienteRazonSocial;
    }
    
    public String getClienteDireccion() {
        return clienteDireccion;
    }
    
    public void setClienteDireccion(String clienteDireccion) {
        this.clienteDireccion = clienteDireccion;
    }
    
    public String getClienteLocalidad() {
        return clienteLocalidad;
    }
    
    public void setClienteLocalidad(String clienteLocalidad) {
        this.clienteLocalidad = clienteLocalidad;
    }
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Business methods
    
    /**
     * Check if this is the current active placement
     */
    public boolean isActive() {
        return "ACTIVO".equals(this.estado) && this.fechaFin == null;
    }
    
    /**
     * Check if this placement is historical (inactive)
     */
    public boolean isHistorical() {
        return "INACTIVO".equals(this.estado) || this.fechaFin != null;
    }
    
    /**
     * Mark this placement as finished with end date
     */
    public void finalizarPlacement(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
        this.estado = "INACTIVO";
    }
    
    /**
     * Get placement duration in days (for historical placements)
     */
    public Long getDuracionDias() {
        if (fechaInicio == null) return null;
        
        LocalDate endDate = fechaFin != null ? fechaFin : LocalDate.now();
        return java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, endDate);
    }
    
    @Override
    public String toString() {
        return "HeladeraUbicacion{" +
                "id=" + id +
                ", numeroComodato='" + numeroComodato + '\'' +
                ", clienteRazonSocial='" + clienteRazonSocial + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", estado='" + estado + '\'' +
                '}';
    }
}