package com.abdoatiia542.GraduationProject.dto;

import org.springframework.web.multipart.MultipartFile;

public record UserDetailsResponse(
        String username,
        String email,
        String role,
        String firstName,
        String lastName,
        String gender,
        Integer birthYear ,
        String image // Multipart instead of String
) {}