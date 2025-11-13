package com.company.auth;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.user.User;
import com.company.user.UserRepository;

@Service
public class AuthService {
  private final UserRepository users;
  private final PasswordEncoder encoder;
  private final JwtService jwt;

  public AuthService(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
    this.users = users; this.encoder = encoder; this.jwt = jwt;
  }

  public void register(String email, String rawPassword) {
    if (users.existsByEmail(email)) throw new IllegalArgumentException("Email already in use");
    User u = new User();
    u.setEmail(email);
    u.setPasswordHash(encoder.encode(rawPassword));
    u.setRole(User.Role.USER);
    users.save(u);
  }

  public AuthResponse login(String email, String rawPassword, long expiresInSeconds) {
    User u = users.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    if (!encoder.matches(rawPassword, u.getPasswordHash()))
      throw new IllegalArgumentException("Invalid credentials");
    String token = jwt.generate(u.getEmail(), u.getRole().name());
    return new AuthResponse(token, "Bearer", expiresInSeconds);
  }
}
