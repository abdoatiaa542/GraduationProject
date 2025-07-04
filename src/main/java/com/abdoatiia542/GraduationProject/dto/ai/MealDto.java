package com.abdoatiia542.GraduationProject.dto.ai;

import com.abdoatiia542.GraduationProject.model.enumerations.MealType;

import java.util.List;

public record MealDto(
        String name,
        MealType type,
        double calories,
        double carbs,
        double fat,
        double protein,
        List<MealItemDto> items
) {}
