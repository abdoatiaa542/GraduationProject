package com.abdoatiia542.GraduationProject.dto.account;


import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotNull;

public record ImageUpdateRequest(
        @NotNull MultipartFile image
) {}
