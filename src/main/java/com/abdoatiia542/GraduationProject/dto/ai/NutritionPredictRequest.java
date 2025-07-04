package com.abdoatiia542.GraduationProject.dto.ai;

public record NutritionPredictRequest(
        double Weight,
        double Height,
        int Age,
        String Gender,
        String Goal,
        String ActivityLevel
) {}