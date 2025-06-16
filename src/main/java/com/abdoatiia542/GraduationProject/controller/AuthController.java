package com.abdoatiia542.GraduationProject.controller;

import com.abdoatiia542.GraduationProject.dto.LoginRequest;
import com.abdoatiia542.GraduationProject.dto.TraineeRegistrationCompleteRequest;
import com.abdoatiia542.GraduationProject.dto.TraineeRegistrationRequest;
import com.abdoatiia542.GraduationProject.service.auth.AuthService;
import com.abdoatiia542.GraduationProject.service.auth.IAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Validated
@RestController
@RequestMapping(value = "api/v1/auth")
public class AuthController {
    private final IAuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

//    @GetMapping("/generate-secure-secret-key")
//    public String generateSecureSecretKey() {
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        return Base64.getEncoder().encodeToString(key.getEncoded());
//    }

    @PostMapping(value = "trainee-registration")
    public ResponseEntity<?> registerTrainee(
            @Valid @RequestBody TraineeRegistrationRequest request) {
        return ResponseEntity.created(URI.create("/api/v1/auth/trainee-registration"))
                .body(service.registerTrainee(request));
    }


    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.accepted().body(service.loginUser(request));
    }


    @PostMapping(value = "complete registration")
    public ResponseEntity<?> completeRegistration(@Valid @RequestBody TraineeRegistrationCompleteRequest request) {
        return ResponseEntity.created(URI.create("/api/v1/auth/trainee-registration"))
                .body(service.completeTraineeRegistration(request));
    }


    @PostMapping(value = "logout")
    public ResponseEntity<?> logoutUser(
            @RequestParam(value = "device-token", required = false) String deviceToken) {

        return ResponseEntity.accepted().body(service.logoutUser(deviceToken));
    }

    @GetMapping(value = "exists-by-email")
    public ResponseEntity<?> existsByEmail(@RequestParam(value = "email") String email) {

        return ResponseEntity.accepted().body(service.existsByEmail(email));
    }


    @GetMapping(value = "exists-by-username")
    public ResponseEntity<?> existsByUsername(
            @RequestParam(value = "username") String username) {

        return ResponseEntity.accepted().body(service.existsByUsername(username));
    }
}