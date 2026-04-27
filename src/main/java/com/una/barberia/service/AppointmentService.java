package com.una.barberia.service;

import com.una.barberia.dto.AppointmentRequest;
import com.una.barberia.exception.AppointmentConflictException;
import com.una.barberia.exception.AppointmentNotFoundException;
import com.una.barberia.model.Appointment;
import com.una.barberia.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    public List<Appointment> findAll(String clienteEmail) {
        if (clienteEmail != null && !clienteEmail.isBlank()) {
            return repository.findByClienteEmailIgnoreCase(clienteEmail);
        }
        return repository.findAll();
    }

    @Transactional
    public Appointment create(AppointmentRequest request) {

        int duration = request.getDuracionMin() != null ? request.getDuracionMin() : 30;

        LocalDateTime start = request.getFechaHora();
        LocalDateTime end = start.plusMinutes(duration);

        List<Appointment> overlapping =
                repository.findOverlapping(start, end, -1L);

        if (!overlapping.isEmpty()) {
            throw new AppointmentConflictException("Ya existe una cita en ese horario");
        }

        Appointment appointment = new Appointment();
        appointment.setClienteNombre(request.getClienteNombre());
        appointment.setClienteEmail(request.getClienteEmail());
        appointment.setClienteTelefono(request.getClienteTelefono());
        appointment.setFechaHora(start);
        appointment.setDuracionMin(duration);
        appointment.setEstado(Appointment.EstadoCita.RESERVADA);

        return repository.save(appointment);
    }

    @Transactional
    public Appointment cancel(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() ->
                        new AppointmentNotFoundException("Cita con id " + id + " no encontrada."));

        appointment.setEstado(Appointment.EstadoCita.CANCELADA);
        return repository.save(appointment);
    }
}