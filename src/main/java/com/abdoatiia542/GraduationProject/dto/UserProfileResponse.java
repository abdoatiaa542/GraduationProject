package com.abdoatiia542.GraduationProject.dto;

public record UserProfileResponse(
        String firstName,
        String lastName,
        String username,
        String email,
        String gender,
        Integer birthYear,
        String token,
        String refreshToken
) {}
