package com.magalu.desafio_backend.service;

import com.magalu.desafio_backend.dto.SchedulingRequestDTO;
import com.magalu.desafio_backend.enums.CommunicationType;
import com.magalu.desafio_backend.model.Scheduling;
import com.magalu.desafio_backend.repository.SchedulingRepository;
import org.springframework.stereotype.Service;

@Service
public class SchedulingService {

    private final SchedulingRepository repository;

    public SchedulingService(SchedulingRepository repository) {
        this.repository = repository;
    }

    public Scheduling createScheduling(SchedulingRequestDTO request) {
        Scheduling scheduling = new Scheduling();
        scheduling.setDateTimeSending(request.dataHora());
        scheduling.setReceiver(request.destinatario());
        scheduling.setMessage(request.mensagem());
        scheduling.setCommunicationType(request.tipoComunicacao());
        return repository.save(scheduling);
    }
}
