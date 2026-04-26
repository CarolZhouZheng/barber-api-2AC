package com.una.barberia.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class AppointmentRequest {

    @NotBlank
    private String clienteNombre;

    @NotBlank
    @Email
    private String clienteEmail;

    private String clienteTelefono;

    @NotNull
    private LocalDateTime fechaHora;

    private Integer duracionMin;

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
}