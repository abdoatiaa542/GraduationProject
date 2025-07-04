package com.abdoatiia542.GraduationProject.dto.ai;

import java.util.List;

public record MealPlanResponse(
        List<MealPlanOptionDto> options
) {
}