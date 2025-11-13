package com.company.board.web;

import java.util.List;

import com.company.board.dto.BoardResponse;

public record BoardPageResponse(
        List<BoardResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {}
