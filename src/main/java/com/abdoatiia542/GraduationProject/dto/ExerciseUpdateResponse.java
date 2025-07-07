package com.abdoatiia542.GraduationProject.dto;

import java.util.Set;

public record ExerciseUpdateResponse(
        Integer id,
        String name,
        String description,
        String reps,
        Integer sets,
        Integer durationSeconds,
        Integer durationRestSeconds,
        Integer caloriesBurned,
        Integer totalCalories,
        String imageLink,
        String videoLink,
        Set<String> bodyFocuses
) {
}