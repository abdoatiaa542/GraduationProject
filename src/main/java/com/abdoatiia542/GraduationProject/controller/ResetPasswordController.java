package com.abdoatiia542.GraduationProject.controller;

import com.abdoatiia542.GraduationProject.constant.RegexConstants;
import com.abdoatiia542.GraduationProject.service.auth.IResetPasswordService;
import com.abdoatiia542.GraduationProject.service.auth.ResetPasswordService;
import jakarta.validation.constraints.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/auth/password")
public class ResetPasswordController {

    private final IResetPasswordService service;

    public ResetPasswordController(ResetPasswordService service) {
        this.service = service;
    }

    @PostMapping(value = "find-account")
    public ResponseEntity<?> findAccount(
            @NotEmpty @RequestParam(value = "username") String usernameOrEmail
    ) {
        return ResponseEntity.accepted().body(service.findAccount(usernameOrEmail));
    }

    @PostMapping(value = "select-email")
    public ResponseEntity<?> requestResetPassword(
            @Min(value = 1) @RequestParam(value = "user-id") Long userId,
            @Email @NotBlank @RequestParam(value = "email") String email
    ) {
        return ResponseEntity.accepted().body(service.requestResetPassword(userId, email));
    }

    @PostMapping(value = "validate-code")
    public ResponseEntity<?> checkResetPasswordCode(
            @Email @NotBlank @RequestParam(value = "email") String email,
            @Min(value = 1000) @RequestParam(value = "code") int code
    ) {
        return ResponseEntity.accepted().body(service.checkResetPasswordCode(email, code));
    }

    @PostMapping(value = "reset-password")
    public ResponseEntity<?> resetPassword(
            @Email @NotBlank @RequestParam(value = "email") String email,
            @Min(value = 1000) @RequestParam(value = "code") int code,
            @Pattern(regexp = RegexConstants.PASSWORD)
            @NotBlank @RequestParam(value = "password") String password
    ) {
        return ResponseEntity.accepted().body(service.resetPassword(email, code, password));
    }

}