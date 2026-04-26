package com.una.barberia.controller;


import com.una.barberia.BarberiaApplication;
import com.una.barberia.model.*;
import com.una.barberia.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {
}
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
public ResponseEntity<Appointment> create(@Valid @RequestBody AppointmentRequest request) {
    Appointment created = service.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
}


@DeleteMapping("/{id}")
public ResponseEntity<Appointment> cancel(@PathVariable Long id) {
    Appointment cancelled = service.cancel(id);
    return ResponseEntity.ok(cancelled);
}
}
