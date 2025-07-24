package com.magalu.desafio_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magalu.desafio_backend.dto.SchedulingRequestDTO;
import com.magalu.desafio_backend.enums.CommunicationType;
import com.magalu.desafio_backend.enums.SchedulingStatus;
import com.magalu.desafio_backend.model.Scheduling;
import com.magalu.desafio_backend.service.SchedulingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SchedulingController.class)
class SchedulingControllerTest {

    // Permite simular requisições HTTP e verificar as respostas
    @Autowired
    private MockMvc mockMvc;

    // Permite converter objetos Java em JSON e vice-versa
    @Autowired
    private ObjectMapper objectMapper;

    // Permite simular o comportamento do SchedulingService
    @MockitoBean
    private SchedulingService schedulingService;

    private SchedulingRequestDTO request;
    private Scheduling scheduling;
    private Long id;

    @BeforeEach
    void setUp() {
        request = new SchedulingRequestDTO(
                LocalDateTime.now().plusHours(1),
                "cliente@gmail.com",
                "mensagem de teste.",
                CommunicationType.EMAIL
        );

        id = 1L;

        scheduling = new Scheduling();
        scheduling.setId(id);
        scheduling.setDateTimeSending(request.dataHora());
        scheduling.setReceiver(request.destinatario());
        scheduling.setMessage(request.mensagem());
        scheduling.setCommunicationType(request.tipoComunicacao());
        scheduling.setStatus(SchedulingStatus.SCHEDULED);
    }

    @Test
    void shouldScheduleCommunication() throws Exception {
        when(schedulingService.createScheduling(any())).thenReturn(scheduling);

        mockMvc.perform(post("/scheduling")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.receiver").value("cliente@gmail.com"));
    }

    @Test
    void shouldReturnBadRequestForInvalidRequest() throws Exception {
        SchedulingRequestDTO invalidRequest = new SchedulingRequestDTO(
                LocalDateTime.now().minusHours(1), // Data no passado
                "cliente@gmail.com",
                "mensagem de teste.",
                CommunicationType.EMAIL
        );

        mockMvc.perform(post("/scheduling")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetScheduledCommunication() throws Exception {
        when(schedulingService.getSchedulingById(id)).thenReturn(scheduling);

        mockMvc.perform(get("/scheduling").param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.status").value("SCHEDULED"));
    }

    @Test
    void shouldReturnNotFoundForNonExistentScheduling() throws Exception {
        when(schedulingService.getSchedulingById(id)).thenReturn(null);

        mockMvc.perform(post("/scheduling").param("id", String.valueOf(id)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteScheduledCommunication() throws Exception {
        doNothing().when(schedulingService).deleteSchedulingById(id);

        mockMvc.perform(delete("/scheduling").param("id", String.valueOf(id)))
                .andExpect(status().isNoContent());
    }
}
