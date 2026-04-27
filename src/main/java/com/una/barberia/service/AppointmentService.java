package com.una.barberia.service;

import com.una.barberia.dto.AppointmentRequest;
import com.una.barberia.exception.*;
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

        List<Appointment> overlap =
                repository.findOverlapping(start, end, -1L);

        if (!overlap.isEmpty()) {
            throw new AppointmentConflictException("Ya existe una cita en ese horario");
        }

        Appointment a = new Appointment();
        a.setClienteNombre(request.getClienteNombre());
        a.setClienteEmail(request.getClienteEmail());
        a.setClienteTelefono(request.getClienteTelefono());
        a.setFechaHora(start);
        a.setDuracionMin(duration);
        a.setEstado(Appointment.EstadoCita.RESERVADA);

        return repository.save(a);
    }

    @Transactional
    public Appointment cancel(Long id) {
        Appointment a = repository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Cita no encontrada"));

        a.setEstado(Appointment.EstadoCita.CANCELADA);
        return repository.save(a);
    }
}