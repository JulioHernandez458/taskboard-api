package com.company.auth;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService service;
  private final long expirationMinutes;

  public AuthController(AuthService service, @Value("${app.jwt.expirationMinutes}") long exp) {
    this.service = service; this.expirationMinutes = exp;
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public void register(@RequestBody @Valid RegisterRequest req) {
    service.register(req.email(), req.password());
  }

  @PostMapping("/login")
  public AuthResponse login(@RequestBody @Valid LoginRequest req) {
    long expiresIn = expirationMinutes * 60;
    return service.login(req.email(), req.password(), expiresIn);
  }
}
