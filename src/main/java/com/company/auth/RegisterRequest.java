package com.company.auth;

import jakarta.validation.constraints.*;

public record RegisterRequest(
  @Email @NotBlank String email,
  @Size(min=8) @NotBlank String password
) {}

