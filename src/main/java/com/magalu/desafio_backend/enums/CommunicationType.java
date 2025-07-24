package com.magalu.desafio_backend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum CommunicationType {
    EMAIL,
    SMS,
    PUSH,
    WHATSAPP;

    @JsonCreator
    public static CommunicationType fromString(String type) {
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid communication type: " + type));
    }
}
