package com.abdoatiia542.GraduationProject.dto.ai;


import java.util.List;

public record MealPlanOptionDto(
        DailyTotalDto daily_total,
        List<MealDto> meals
) {}