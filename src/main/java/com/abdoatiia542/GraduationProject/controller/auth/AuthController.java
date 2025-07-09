package com.abdoatiia542.GraduationProject.controller.auth;

import com.abdoatiia542.GraduationProject.dto.LoginRequest;
import com.abdoatiia542.GraduationProject.dto.TraineeRegistrationCompleteRequest;
import com.abdoatiia542.GraduationProject.dto.TraineeRegistrationRequest;
import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.service.auth.authentication.AuthService;
import com.abdoatiia542.GraduationProject.service.auth.authentication.IAuthService;
import com.abdoatiia542.GraduationProject.service.jwt.JwtService;
import com.abdoatiia542.GraduationProject.utils.Response.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Validated
@RestController
@RequestMapping(value = "api/v1/auth")
public class AuthController {
    private final IAuthService service;


    public AuthController(AuthService service, JwtService jwtService) {
        this.service = service;

    }

    @PostMapping("/trainee-registration")
    public ResponseEntity<?> registerTrainee(@Valid @RequestBody TraineeRegistrationRequest request) {
        return ResponseEntity.created(URI.create("/api/v1/auth/trainee-registration"))
                .body(service.registerTrainee(request));
    }

    @PostMapping(value = "/complete-registration", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> completeRegistration(@ModelAttribute TraineeRegistrationCompleteRequest request) {
        ApiResponse response = (ApiResponse) service.completeTraineeRegistration(request);
        return ResponseUtil.okOrBadRequest(response);
    }

    // ---------------- Login ----------------

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest request) {
        ApiResponse response = (ApiResponse) service.loginUser(request);
        return ResponseUtil.okOrUnauthorized(response);
    }

    // ---------------- Logout ----------------

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestParam(value = "device-token", required = false) String deviceToken) {
        ApiResponse response = (ApiResponse) service.logoutUser(deviceToken);
        return ResponseUtil.accepted(response);
    }


    @PostMapping(value = "refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(service.refreshToken(authHeader));
    }
}