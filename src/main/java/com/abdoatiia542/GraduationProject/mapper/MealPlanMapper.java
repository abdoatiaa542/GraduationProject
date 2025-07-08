package com.abdoatiia542.GraduationProject.mapper;

import com.abdoatiia542.GraduationProject.dto.ai.MealPlanOptionDto;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.food.Meal;
import com.abdoatiia542.GraduationProject.model.food.MealItems;
import com.abdoatiia542.GraduationProject.model.food.MealPlan;

import java.time.LocalDate;
import java.util.List;

public class MealPlanMapper {

    public static MealPlan toMealPlan(MealPlanOptionDto dto, Trainee trainee, LocalDate date) {
        MealPlan mealPlan = MealPlan.builder()
                .trainee(trainee)
                .date(date)
                .totalCalories(dto.daily_total().calories())
                .totalCarbs(dto.daily_total().carbs())
                .totalProtein(dto.daily_total().protein())
                .totalFat(dto.daily_total().fat())
                .build();

        List<Meal> meals = dto.meals().stream().map(mealDto -> {
            Meal meal = Meal.builder()
                    .name(mealDto.name())
                    .calories(mealDto.calories())
                    .carbs(mealDto.carbs())
                    .protein(mealDto.protein())
                    .fat(mealDto.fat())
                    .type(mealDto.type())
                    .mealPlan(mealPlan)
                    .build();

            List<MealItems> items = mealDto.items().stream().map(itemDto ->
                    MealItems.builder()
                            .name(itemDto.name())
                            .imageUrl(itemDto.imageUrl())
                            .meal(meal)
                            .build()
            ).toList();

            meal.setItems(items);
            return meal;
        }).toList();

        mealPlan.setMeals(meals);
        return mealPlan;
    }
}
