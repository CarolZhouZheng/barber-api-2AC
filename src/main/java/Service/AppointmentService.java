package Service;
package com.una.barberia.Service;

import com.barber.appointments.dto.AppointmentRequest;
import com.barber.appointments.exception.AppointmentConflictException;
import com.barber.appointments.exception.AppointmentNotFoundException;
import com.barber.appointments.model.Appointment;
import com.barber.appointments.repository.AppointmentRepository;
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

        int duration = (request.getDuracionMin() != null) ? request.getDuracionMin() : 30;

        LocalDateTime start = request.getFechaHora();
        LocalDateTime end = start.plusMinutes(duration);


        List<Appointment> overlapping = repository.findOverlapping(start, end, -1L);
        if (!overlapping.isEmpty()) {
            Appointment conflict = overlapping.get(0);
            throw new AppointmentConflictException(
                    "Existe solapamiento con la cita #" + conflict.getId()
                            + " de " + conflict.getClienteNombre()
                            + " a las " + conflict.getFechaHora()
                            + " (duración " + conflict.getDuracionMin() + " min)."
            );
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
                .orElseThrow(() -> new AppointmentNotFoundException("Cita con id " + id + " no encontrada."));

        appointment.setEstado(Appointment.EstadoCita.CANCELADA);
        return repository.save(appointment);
    }
}
//me saco canas github
