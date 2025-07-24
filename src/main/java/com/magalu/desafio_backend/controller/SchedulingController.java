package com.magalu.desafio_backend.controller;

import com.magalu.desafio_backend.dto.SchedulingRequestDTO;
import com.magalu.desafio_backend.model.Scheduling;
import com.magalu.desafio_backend.service.SchedulingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduling")
public class SchedulingController {

    private final SchedulingService service;

    public SchedulingController(SchedulingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Scheduling> createScheduling(@Valid @RequestBody SchedulingRequestDTO request) {
        Scheduling scheduling = service.createScheduling(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduling);
    }
}
