package com.company.boardList.web;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BoardListUpdateRequest(
        @NotBlank
        @Size(min = 3, max = 60)
        String title
) {}

