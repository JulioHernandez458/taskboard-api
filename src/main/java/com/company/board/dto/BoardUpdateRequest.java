package com.company.board.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BoardUpdateRequest(
        @NotBlank
        @Size(min = 3, max = 80)
        String title,
        boolean archived
) {}

