package com.abdoatiia542.GraduationProject.controller;

import com.abdoatiia542.GraduationProject.service.auth.IConfirmEmailService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/auth/email")
public class ConfirmEmailController {

    private final IConfirmEmailService service;

    public ConfirmEmailController(IConfirmEmailService service) {
        this.service = service;
    }

    @PostMapping(value = "send")
    public ResponseEntity<?> sendConfirmationEmail(
            @Min(value = 1) @RequestParam(value = "user-id") Long userId,
            @Email @NotBlank @RequestParam(value = "email") String email
    ) {
        return ResponseEntity.accepted().body(service.sendConfirmationEmailWithResponse(userId, email));
    }

    @PostMapping(value = "confirm")
    public ResponseEntity<?> confirmAccount(
            @Email @NotBlank @RequestParam(value = "email") String email,
            @Min(value = 1000) @RequestParam(value = "code") int code
    ) {
        return ResponseEntity.accepted().body(service.confirmAccount(email, code));
    }

}