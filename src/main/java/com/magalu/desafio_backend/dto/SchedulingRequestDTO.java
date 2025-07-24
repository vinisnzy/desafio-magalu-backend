package com.magalu.desafio_backend.dto;

import com.magalu.desafio_backend.enums.CommunicationType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SchedulingRequestDTO(
        @NotNull
        @Future
        LocalDateTime dataHora,

        @NotBlank
        String destinatario,

        @NotBlank
        String mensagem,

        @NotNull
        CommunicationType tipoComunicacao
) {
}
