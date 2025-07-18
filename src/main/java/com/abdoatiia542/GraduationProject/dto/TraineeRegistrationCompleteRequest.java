package com.abdoatiia542.GraduationProject.dto;

import com.abdoatiia542.GraduationProject.model.enumerations.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record TraineeRegistrationCompleteRequest(

        @Nullable
        String firstName,

        @Nullable
        String lastName,

        @Nullable
        Gender gender,

        @Nullable
        Integer birthYear ,

        @Nullable
        MultipartFile image
) {

}
