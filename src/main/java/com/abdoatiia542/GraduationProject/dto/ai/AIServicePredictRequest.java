package com.abdoatiia542.GraduationProject.dto.ai;

import com.abdoatiia542.GraduationProject.model.Trainee;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;


@Builder
public record AIServicePredictRequest(
        double Weight,
        double Height,
        int Age,
        String Gender,
        String Goal,
        String ActivityLevel
) {

    public static AIServicePredictRequest buildRequestFromTrainee(Trainee trainee) {
        Objects.requireNonNull(trainee, "Trainee must not be null");

        if (trainee.getBirthYear() == null)
            throw new IllegalArgumentException("Birth year is required to calculate age.");

        int age = LocalDate.now().getYear() - trainee.getBirthYear();

        return AIServicePredictRequest.builder()
                .Weight(Objects.requireNonNull(trainee.getWeight(), "Weight must not be null"))
                .Height(Objects.requireNonNull(trainee.getHeight(), "Height must not be null"))
                .Age(age)
                .Goal(Objects.requireNonNull(trainee.getGoal().getValue(), "Goal must not be null"))
                .ActivityLevel(Objects.requireNonNull(trainee.getActivityLevel().getValue(), "Activity level must not be null"))
                .Gender(Objects.requireNonNull(trainee.getGender().getValue(), "Gender must not be null"))
                .build();
    }

}