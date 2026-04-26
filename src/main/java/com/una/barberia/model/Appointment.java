package com.una.barberia.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String clienteNombre;

    @Column(nullable = false)
    private String clienteEmail;

    private String clienteTelefono;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    private Integer duracionMin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCita estado;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    public enum EstadoCita {
        RESERVADA, CANCELADA
    }

    @PrePersist
    protected void onCreate() {
        this.creadoEn = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = EstadoCita.RESERVADA;
        }
        if (this.duracionMin == null) {
            this.duracionMin = 30;
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public String getClienteEmail() { return clienteEmail; }
    public void setClienteEmail(String clienteEmail) { this.clienteEmail = clienteEmail; }

    public String getClienteTelefono() { return clienteTelefono; }
    public void setClienteTelefono(String clienteTelefono) { this.clienteTelefono = clienteTelefono; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public Integer getDuracionMin() { return duracionMin; }
    public void setDuracionMin(Integer duracionMin) { this.duracionMin = duracionMin; }

    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }

    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
}
