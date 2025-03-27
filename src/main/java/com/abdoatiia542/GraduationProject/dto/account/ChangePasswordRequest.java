package com.abdoatiia542.GraduationProject.dto.account;

import com.abdoatiia542.GraduationProject.constant.RegexConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordRequest(
        @NotBlank
        String password,

        @NotBlank
        @Pattern(regexp = RegexConstants.PASSWORD)
        String newPassword
) {
}