package com.abdoatiia542.GraduationProject.controller.auth;
import com.abdoatiia542.GraduationProject.dto.LoginRequest;
import com.abdoatiia542.GraduationProject.dto.TraineeRegistrationCompleteRequest;
import com.abdoatiia542.GraduationProject.dto.TraineeRegistrationRequest;
import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.service.auth.AuthService;
import com.abdoatiia542.GraduationProject.service.auth.IAuthService;
import com.abdoatiia542.GraduationProject.utils.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        return ResponseUtil.createdOrConflict(response, "/api/v1/auth/trainee-registration");
    }

    @PostMapping("/complete-registration")
    public ResponseEntity<?> completeRegistration(@Valid @RequestBody TraineeRegistrationCompleteRequest request) {
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

    @GetMapping("/exists-by-email")
    public ResponseEntity<?> existsByEmail(@RequestParam String email) {
        com.abdoatiia542.GraduationProject.dto.api.ApiResponse response = (com.abdoatiia542.GraduationProject.dto.api.ApiResponse) service.existsByEmail(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists-by-username")
    public ResponseEntity<?> existsByUsername(@RequestParam String username) {
        com.abdoatiia542.GraduationProject.dto.api.ApiResponse response = (com.abdoatiia542.GraduationProject.dto.api.ApiResponse) service.existsByUsername(username);
        return ResponseEntity.ok(response);
    }


//    @PostMapping(value = "refresh-token")
//    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authHeader) {
//        return ResponseEntity.ok(service.refreshToken(authHeader));
//    }
}