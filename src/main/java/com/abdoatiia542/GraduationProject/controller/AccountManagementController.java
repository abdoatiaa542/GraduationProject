package com.abdoatiia542.GraduationProject.controller;

import com.abdoatiia542.GraduationProject.dto.account.ChangePasswordRequest;
import com.abdoatiia542.GraduationProject.service.account.IAccountManagementService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/my-account")
public class AccountManagementController {


    private final IAccountManagementService service;


    @PostMapping(value = "password/change")
    ResponseEntity<?> changePassword(
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        return ResponseEntity.accepted().body(service.changePassword(request));
    }


    @PutMapping(value = "picture")
    ResponseEntity<?> uploadProfilePicture(
            @NotEmpty @RequestPart(value = "picture") MultipartFile pictureFile
    ) {
        return ResponseEntity.accepted().body(service.uploadProfilePicture(pictureFile));
    }


    @DeleteMapping(value = "picture")
    ResponseEntity<?> removeProfilePicture() {
        return ResponseEntity.accepted().body(service.removeProfilePicture());
    }

    @GetMapping(value = "authorities")
    ResponseEntity<?> getAuthorities() {
        return ResponseEntity.ok().body(service.getAuthorities());
    }


}
