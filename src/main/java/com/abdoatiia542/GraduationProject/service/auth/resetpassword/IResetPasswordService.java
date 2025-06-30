package com.abdoatiia542.GraduationProject.service.auth.resetpassword;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;

public interface IResetPasswordService {
    ApiResponse findAccount(String usernameOrEmail);

    ApiResponse requestResetPassword(Long userId, String email);

    ApiResponse checkResetPasswordCode(String email, int code);

    ApiResponse resetPassword(String email, int code, String password);

}
