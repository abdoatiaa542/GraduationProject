package com.abdoatiia542.GraduationProject.dto.food;

import java.util.List;

public record MealPlanResponseDto(
        List<MealDto> meals,
        double totalCalories,
        double totalProtein,
        double totalCarbs,
        double totalFat
) {}
