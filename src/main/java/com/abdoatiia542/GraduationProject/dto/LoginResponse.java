package com.abdoatiia542.GraduationProject.dto;

import com.abdoatiia542.GraduationProject.model.enumerations.Gender;

public record LoginResponse(
        String message,
        String token,
        String refreshToken,
        String email,
        String username,
        String role,
        String firstName,
        String lastName,
        Gender gender,
        Integer birthYear,
        boolean isMeasurementsSet

) {

}
