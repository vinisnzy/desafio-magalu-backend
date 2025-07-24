package com.magalu.desafio_backend.service;

import com.magalu.desafio_backend.dto.SchedulingRequestDTO;
import com.magalu.desafio_backend.enums.SchedulingStatus;
import com.magalu.desafio_backend.exceptions.ResourceNotFoundException;
import com.magalu.desafio_backend.model.Scheduling;
import com.magalu.desafio_backend.repository.SchedulingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final SchedulingRepository repository;

    public Scheduling createScheduling(SchedulingRequestDTO request) {
        Scheduling scheduling = new Scheduling();
        scheduling.setDateTimeSending(request.dataHora());
        scheduling.setReceiver(request.destinatario());
        scheduling.setMessage(request.mensagem());
        scheduling.setCommunicationType(request.tipoComunicacao());
        scheduling.setStatus(SchedulingStatus.SCHEDULED);
        return repository.save(scheduling);
    }

    public Scheduling getSchedulingById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scheduling not found with id: " + id));
    }

    public void deleteSchedulingById(Long id) {
        repository.deleteById(id);
    }
}
