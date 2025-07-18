package com.abdoatiia542.GraduationProject.dto;

import com.abdoatiia542.GraduationProject.annotations.UserUnique;
import com.abdoatiia542.GraduationProject.constant.RegexConstants;
import com.abdoatiia542.GraduationProject.model.enumerations.UserUniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public record TraineeRegistrationRequest(

        @NotBlank
        @UserUnique(constraint = UserUniqueConstraint.USERNAME, message = "this username is already used")
        String username,

        @Email
        @NotBlank
        @UserUnique(constraint = UserUniqueConstraint.EMAIL, message = "this email is already used")
        String email,

        @NotBlank
        @Pattern(
                regexp = RegexConstants.PASSWORD,
                message = "Password must be 8-32 characters and include uppercase, lowercase, number, and special character"
        )
        String password

) {

}
