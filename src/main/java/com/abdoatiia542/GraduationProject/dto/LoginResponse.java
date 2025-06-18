package com.abdoatiia542.GraduationProject.dto;

public record LoginResponse(
        String message,
        String token,
        String refreshToken,
        String email,
        String username,
        String gender,
        String role
) {

}
