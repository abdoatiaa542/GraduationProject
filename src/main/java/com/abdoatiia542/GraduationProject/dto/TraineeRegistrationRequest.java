package com.abdoatiia542.GraduationProject.dto;

import com.abdoatiia542.GraduationProject.annotations.UserUnique;
import com.abdoatiia542.GraduationProject.constant.RegexConstants;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.enumerations.Gender;
import com.abdoatiia542.GraduationProject.model.enumerations.UserUniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;


public record TraineeRegistrationRequest(

        @NotBlank
        @UserUnique(constraint = UserUniqueConstraint.USERNAME, message = "this username is already used")
        String username,

        @Email
        @NotBlank
        @UserUnique(constraint = UserUniqueConstraint.EMAIL, message = "this email is already used")
        String email,

        @NotBlank
        @Pattern(regexp = RegexConstants.PASSWORD)
        String password

//        @NotNull
//        Gender gender,
//
//        LocalDate birthDate

) {

}
