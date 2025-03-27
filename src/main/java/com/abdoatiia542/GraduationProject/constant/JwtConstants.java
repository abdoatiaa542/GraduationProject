package com.abdoatiia542.GraduationProject.constant;

public interface JwtConstants {
    String JWT_HEADER_PREFIX = "Bearer ";
    String ISSUER = "FitAI";
    String REGISTRATION_CLAIM = "registration";
    String ROLE_CLAIM = "role";
    String EMAIL_CLAIM = "email";

    long EXPIRATION_IN_SECONDS = 1_000 * 60 * 60 * 24 * 7;
}