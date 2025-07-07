package com.abdoatiia542.GraduationProject.dto.account;

public record ProfileRequest(
        String firstName,
        String lastName,
        String email,
        Integer birthYear
) {
}
