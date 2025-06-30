package com.abdoatiia542.GraduationProject.dto.food;

import com.abdoatiia542.GraduationProject.model.enumerations.MealType;

public record MealDto(
        String name,
        MealType type
) {}
