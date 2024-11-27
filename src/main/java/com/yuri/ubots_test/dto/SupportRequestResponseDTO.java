package com.yuri.ubots_test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.UUID;

public record SupportRequestResponseDTO(
        UUID publicId,
        String subject,
        String message,
        ZonedDateTime requestedAt,
        ZonedDateTime updatedAt,
        Long id
) {
}
