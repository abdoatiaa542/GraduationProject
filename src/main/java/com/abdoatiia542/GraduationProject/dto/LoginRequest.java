package com.abdoatiia542.GraduationProject.dto;


import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.Nullable;

public record LoginRequest(
        @NotBlank
        String username,

        @NotBlank
        String password,

        @Nullable
        String deviceToken
) {
}