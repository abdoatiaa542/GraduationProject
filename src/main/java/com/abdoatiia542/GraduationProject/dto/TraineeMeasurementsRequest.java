package com.abdoatiia542.GraduationProject.dto;

import com.abdoatiia542.GraduationProject.model.embeddables.BodyFatRange;
import com.abdoatiia542.GraduationProject.model.enumerations.ActivityLevel;
import com.abdoatiia542.GraduationProject.model.enumerations.Goal;
import com.abdoatiia542.GraduationProject.model.enumerations.TrainingLevel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record TraineeMeasurementsRequest(

        @NotNull
        @Min(100)
        @Max(250)
        Double height,

        @NotNull
        @Min(30)
        @Max(300)
        Double weight,

        @NotNull
        @Min(30)
        @Max(300)
        Double targetWeight,

        @NotNull
        Goal goal,

        @NotNull
        @Valid
        BodyFatRange bodyFat,

        @NotNull
        @Valid
        BodyFatRange targetBodyFat,

        @NotNull
        TrainingLevel trainingLevel,

        @NotNull
        ActivityLevel activityLevel


) {
}
