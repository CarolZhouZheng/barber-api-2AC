package com.una.barberia.Repository;

import com.una.barberia.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

//ojala se arregle x2