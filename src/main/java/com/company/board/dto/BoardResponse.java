package com.company.board.dto;

import java.time.Instant;

public record BoardResponse(
        Long id,
        String title,
        boolean archived,
        Instant createdAt
) {}

