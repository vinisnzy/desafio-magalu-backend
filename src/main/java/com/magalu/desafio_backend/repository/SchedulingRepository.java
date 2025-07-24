package com.magalu.desafio_backend.repository;

import com.magalu.desafio_backend.model.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {
}