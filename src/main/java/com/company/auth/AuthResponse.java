package com.company.auth;

public record AuthResponse(String accessToken, String tokenType, long expiresIn) {}
