package com.abdoatiia542.GraduationProject.mapper;

import com.abdoatiia542.GraduationProject.dto.ai.MealDto;
import com.abdoatiia542.GraduationProject.dto.ai.MealItemDto;
import com.abdoatiia542.GraduationProject.model.food.Meal;

public class MealMapper {

    public static MealDto toDto(Meal meal) {
       return new MealDto(
                meal.getName(),
                meal.getType(),
                meal.getCalories(),
                meal.getCarbs(),
                meal.getFat(),
               meal.getProtein(),
               meal.getItems().stream()
                        .map(item -> new MealItemDto(item.getName(), item.getImageUrl()))
                        .toList()
        );
    }
}
