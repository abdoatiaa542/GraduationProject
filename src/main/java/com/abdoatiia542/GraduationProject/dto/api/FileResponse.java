package com.abdoatiia542.GraduationProject.dto.api;

import org.springframework.http.HttpHeaders;

public record FileResponse(
        byte[] data,

        HttpHeaders headers
) {
}