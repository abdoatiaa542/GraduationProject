package com.abdoatiia542.GraduationProject.controller.OAuth2;

import com.abdoatiia542.GraduationProject.service.OAuth2.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;

    @PostMapping("/google")
    public ResponseEntity<?> authenticateWithGoogle(@RequestParam String code) {
        return ResponseEntity.status(HttpStatus.CREATED).body(oAuth2Service.authenticateWithGoogle(code));
    }
}
