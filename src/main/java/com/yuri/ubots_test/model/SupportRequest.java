package com.yuri.ubots_test.model;

import com.yuri.ubots_test.util.IdGenerator;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
public class SupportRequest implements Entity {

    private final Long id = IdGenerator.nextId("support-request");
    private String subject;
    private String message;
    private final ZonedDateTime requestedAt = ZonedDateTime.now();
    private ZonedDateTime updatedAt;
    private final UUID publicId = UUID.randomUUID();

}
