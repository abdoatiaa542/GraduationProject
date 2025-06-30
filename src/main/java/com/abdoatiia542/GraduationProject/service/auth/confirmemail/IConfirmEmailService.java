package com.abdoatiia542.GraduationProject.service.auth.confirmemail;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import jakarta.transaction.Transactional;

public interface  IConfirmEmailService {

    void sendConfirmationEmail(Long userId, String email);

    @Transactional
    ApiResponse sendConfirmationEmailWithResponse(Long userId, String email);

    ApiResponse confirmAccount(String email, int code);
}
