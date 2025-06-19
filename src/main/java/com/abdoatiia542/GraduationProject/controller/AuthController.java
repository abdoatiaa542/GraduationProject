package com.abdoatiia542.GraduationProject.controller;

import com.abdoatiia542.GraduationProject.dto.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.LoginRequest;
import com.abdoatiia542.GraduationProject.dto.TraineeRegistrationCompleteRequest;
import com.abdoatiia542.GraduationProject.dto.TraineeRegistrationRequest;
import com.abdoatiia542.GraduationProject.service.auth.AuthService;
import com.abdoatiia542.GraduationProject.service.auth.IAuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
@PostMapping("/trainee-registration")
public ResponseEntity<?> registerTrainee(@Valid @RequestBody TraineeRegistrationRequest request) {
    ApiResponse response = (ApiResponse) service.registerTrainee(request);

    if (response.success()) {
        return ResponseEntity.created(URI.create("/api/v1/auth/trainee-registration")).body(response);
    } else {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}



    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest request) {
        ApiResponse response = (ApiResponse) service.loginUser(request);

        if (response.success()) {
            return ResponseEntity.ok(response); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // 401 Unauthorized
        }
    }



    @PostMapping(value = "/complete-registration")
    public ResponseEntity<?> completeRegistration(@Valid @RequestBody TraineeRegistrationCompleteRequest request) {
        ApiResponse response = (ApiResponse) service.completeTraineeRegistration(request);

        if (response.success()) {
            return ResponseEntity
                    .created(URI.create("/api/v1/auth/complete-registration"))
                    .body(response); // 201 Created
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response); // 400 Bad Request
        }
    }



    @PostMapping(value = "logout")
    public ResponseEntity<?> logoutUser(
            @RequestParam(value = "device-token", required = false) String deviceToken) {

        return ResponseEntity.accepted().body(service.logoutUser(deviceToken));
    }

    @GetMapping(value = "exists-by-email")
    public ResponseEntity<?> existsByEmail(@RequestParam(value = "email") String email) {

        ApiResponse response = (ApiResponse) service.existsByEmail(email);

        if (response.success()) {
            return ResponseEntity.ok(response); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // 401 Unauthorized
        }
    }


    @GetMapping(value = "exists-by-username")
    public ResponseEntity<?> existsByUsername(
            @RequestParam(value = "username") String username) {
        ApiResponse response = (ApiResponse) service.existsByUsername(username);

        if (response.success()) {
            return ResponseEntity.ok(response); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // 401 Unauthorized
        }
    }


//    @PostMapping(value = "refresh-token")
//    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authHeader) {
//        return ResponseEntity.ok(service.refreshToken(authHeader));
//    }
}