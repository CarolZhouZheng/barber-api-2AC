package com.una.barberia.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class AppointmentRequest {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String clienteNombre;

    @NotBlank(message = "El email del cliente es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String clienteEmail;

    private String clienteTelefono;

    @NotNull(message = "La fecha y hora son obligatorias")
    @Future(message = "La fecha y hora deben ser en el futuro")
    private LocalDateTime fechaHora;

    @Positive(message = "La duración debe ser un número positivo")
    private Integer duracionMin;

    // Getters and Setters
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
