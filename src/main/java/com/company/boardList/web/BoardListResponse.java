package com.company.boardList.web;


import java.time.Instant;

public record BoardListResponse(
        Long id,
        Long boardId,
        String title,
        int position,
        Instant createdAt
) {}
