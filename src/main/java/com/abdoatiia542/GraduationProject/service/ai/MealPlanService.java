package com.abdoatiia542.GraduationProject.service.ai;

import com.abdoatiia542.GraduationProject.dto.ai.*;
import com.abdoatiia542.GraduationProject.model.food.Meal;
import com.abdoatiia542.GraduationProject.model.food.MealItems;
import com.abdoatiia542.GraduationProject.model.food.MealPlan;
import com.abdoatiia542.GraduationProject.repository.TraineeRepository;
import com.abdoatiia542.GraduationProject.repository.food.MealPlanRepository;
import jakarta.transaction.Transactional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MealPlanService {


    private static final String MEAL_PLAN_URL = "https://abdowa7eed.pythonanywhere.com/api/meal-plan-text";
    private static final String NUTRITION_PREDICT_URL = "https://abdowa7eed.pythonanywhere.com/api/nutrition-predict";
    private final MealPlanRepository mealPlanRepository;
    private final TraineeRepository traineeRepository;
    final RestTemplate restTemplate = new RestTemplate();

    public MealPlanService(MealPlanRepository mealPlanRepository, TraineeRepository traineeRepository) {
        this.mealPlanRepository = mealPlanRepository;
        this.traineeRepository = traineeRepository;
    }

    public MealPlanOptionDto generateMealPlan(NutritionPredictRequest request, Long traineeId) {
        // Step 1
        NutritionResponse nutrition = callNutritionPredictAPI(request);

        // Step 2
        MealPlanOptionDto firstOption = callMealPlanAPI(nutrition);

        // Step 3
        saveMealPlan(firstOption, traineeId);

        return firstOption;
    }

    // ✅ Step 1: Call nutrition-predict API
    private NutritionResponse callNutritionPredictAPI(NutritionPredictRequest request) {
        ResponseEntity<List<NutritionResponse>> response = restTemplate.exchange(
                NUTRITION_PREDICT_URL,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {
                }
        );

        return response.getBody().get(0);
    }

    // ✅ Step 2: Call meal-plan-text API
    private MealPlanOptionDto callMealPlanAPI(NutritionResponse nutrition) {
        Map<String, Double> nutritionInput = Map.of(
                "calories", nutrition.calories(),
                "carbs", nutrition.carbs(),
                "fat", nutrition.fat(),
                "protein", nutrition.protein()
        );

        ResponseEntity<MealPlanResponse> response = restTemplate.exchange(
                MEAL_PLAN_URL,
                HttpMethod.POST,
                new HttpEntity<>(nutritionInput),
                MealPlanResponse.class
        );

        return response.getBody().options().get(0);
    }

    // ✅ Step 3: Save result to DB
    private void saveMealPlan(MealPlanOptionDto option, Long traineeId) {
        MealPlan mealPlan = MealPlan.builder()
                .totalCalories(option.daily_total().calories())
                .totalCarbs(option.daily_total().carbs())
                .totalProtein(option.daily_total().protein())
                .totalFat(option.daily_total().fat())
                .trainee(traineeRepository.findById(traineeId).orElseThrow())
                .build();

        List<Meal> meals = new ArrayList<>();

        for (MealDto mealDto : option.meals()) {
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
            meals.add(meal);
        }

        mealPlan.setMeals(meals);
        mealPlanRepository.save(mealPlan);
    }
}