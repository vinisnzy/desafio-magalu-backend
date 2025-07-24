package com.magalu.desafio_backend.service;

import com.magalu.desafio_backend.dto.SchedulingRequestDTO;
import com.magalu.desafio_backend.enums.CommunicationType;
import com.magalu.desafio_backend.enums.SchedulingStatus;
import com.magalu.desafio_backend.exceptions.ResourceNotFoundException;
import com.magalu.desafio_backend.model.Scheduling;
import com.magalu.desafio_backend.repository.SchedulingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SchedulingServiceTest {

    @InjectMocks
    private SchedulingService service;

    @Mock
    private SchedulingRepository repository;

    private SchedulingRequestDTO request;
    private Scheduling scheduling;
    private Long id;

    @BeforeEach
    void setUp() {
        id = 1L;

        request = new SchedulingRequestDTO(
                LocalDateTime.now().plusHours(1),
                "cliente@gmail.com",
                "mensagem de teste.",
                CommunicationType.EMAIL
        );

        scheduling = new Scheduling();
        scheduling.setId(id);
        scheduling.setDateTimeSending(request.dataHora());
        scheduling.setReceiver(request.destinatario());
        scheduling.setMessage(request.mensagem());
        scheduling.setCommunicationType(request.tipoComunicacao());
        scheduling.setStatus(SchedulingStatus.SCHEDULED);
    }

    @Test
    void shouldCreateScheduling() {
        when(repository.save(scheduling)).thenReturn(scheduling);

        Scheduling result = service.createScheduling(request);

        assertNotNull(result);
        assertEquals(scheduling, result);
    }

    @Test
    void shouldGetSchedulingById() {
        when(repository.findById(id)).thenReturn(Optional.of(scheduling));

        Scheduling result = service.getSchedulingById(id);
        assertNotNull(result);
        assertEquals(scheduling, result);
    }

    @Test
    void shouldThrowExceptionWhenSchedulingNotFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.getSchedulingById(id);
        });

        assertEquals("Scheduling not found with id: " + id, exception.getMessage());
    }

    @Test
    void shouldDeleteSchedulingById() {
        service.deleteSchedulingById(id);

        verify(repository).deleteById(id);
    }
}