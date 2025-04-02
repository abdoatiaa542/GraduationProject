package com.abdoatiia542.GraduationProject.service.auth;

import com.abdoatiia542.GraduationProject.dto.ApiResponse;
import jakarta.transaction.Transactional;

public interface  IConfirmEmailService {

    void sendConfirmationEmail(Long userId, String email);

    @Transactional
    ApiResponse sendConfirmationEmailWithResponse(Long userId, String email);

    ApiResponse confirmAccount(String email, int code);
}
