package com.una.barberia.repository;

import com.una.barberia.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByClienteEmailIgnoreCase(String clienteEmail);

    @Query("""
        SELECT a FROM Appointment a
        WHERE a.estado = 'RESERVADA'
          AND a.id <> :excludeId
          AND a.fechaHora < :newEnd
          AND FUNCTION('TIMESTAMPADD', MINUTE, a.duracionMin, a.fechaHora) > :newStart
    """)
    List<Appointment> findOverlapping(
            @Param("newStart") LocalDateTime newStart,
            @Param("newEnd") LocalDateTime newEnd,
            @Param("excludeId") Long excludeId
    );
}