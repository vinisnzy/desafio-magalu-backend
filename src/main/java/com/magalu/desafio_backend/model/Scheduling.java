package com.magalu.desafio_backend.model;

import com.magalu.desafio_backend.enums.CommunicationType;
import com.magalu.desafio_backend.enums.SchedulingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "communication_scheduling")
@Getter
@Setter
@NoArgsConstructor
public class Scheduling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "date_time_sending", nullable = false)
    private LocalDateTime dateTimeSending;

    @Column(name = "receiver", nullable = false)
    private String receiver;

    @Column(name = "message", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "communication_type", nullable = false)
    private CommunicationType communicationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SchedulingStatus status;
}
