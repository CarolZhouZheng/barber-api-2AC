package Repository;
package com.barberia.appointments.repository;

import com.barberia.appointments.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByClienteEmailIgnoreCase(String clienteEmail);


    @Query("""
        SELECT a FROM Appointment a
        WHERE a.estado = 'RESERVADA'
          AND a.id <> :excludeId
          AND a.fechaHora < :newEnd
          AND (a.fechaHora + (a.duracionMin * 1 MINUTE)) > :newStart
        """)
    List<Appointment> findOverlapping(
            @Param("newStart") LocalDateTime newStart,
            @Param("newEnd") LocalDateTime newEnd,
            @Param("excludeId") Long excludeId
    );
}
//vivan las mujeres
