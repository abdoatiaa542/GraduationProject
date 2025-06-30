package com.abdoatiia542.GraduationProject.dto.food;


public record MealPlanRequestDto(
        double calories,
        double protein,
        double fat,
        double carbs,
        boolean vegetarian
) {}