package com.company.boardList.web;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BoardListReorderRequest(
        @NotNull
        @NotEmpty
        List<Long> listIds
) {}
