package com.una.barberia.controller;

import com.una.barberia.dto.AppointmentRequest;
import com.una.barberia.model.Appointment;
import com.una.barberia.service.AppointmentService;

import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin("*")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAll(
            @RequestParam(required = false) String clienteEmail) {
        return ResponseEntity.ok(service.findAll(clienteEmail));
    }

    @PostMapping
    public ResponseEntity<Appointment> create(
            @Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Appointment> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancel(id));
    }
}