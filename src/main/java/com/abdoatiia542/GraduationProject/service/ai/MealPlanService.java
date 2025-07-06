package com.abdoatiia542.GraduationProject.service.ai;

import com.abdoatiia542.GraduationProject.dto.ai.*;
import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.mapper.MealMapper;
import com.abdoatiia542.GraduationProject.mapper.MealPlanMapper;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.food.Meal;
import com.abdoatiia542.GraduationProject.model.food.MealItems;
import com.abdoatiia542.GraduationProject.model.food.MealPlan;
import com.abdoatiia542.GraduationProject.repository.TraineeRepository;
import com.abdoatiia542.GraduationProject.repository.food.MealPlanRepository;
import com.abdoatiia542.GraduationProject.utils.context.ContextHolderUtils;
import jakarta.transaction.Transactional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public ApiResponse generateMealPlan() {

        Trainee trainee = ContextHolderUtils.getTrainee();

        //  Step 1: Build request from trainee object
        AIServicePredictRequest request = AIServicePredictRequest.buildRequestFromTrainee(trainee);

        // Step 2
        NutritionResponse nutrition = callNutritionPredictAPI(request);

        // Step 3
        MealPlanOptionDto firstOption = callMealPlanAPI(nutrition);

        // Step 4
        saveMealPlan(firstOption, trainee.getId());

        return ApiResponse.success("Meal plan generated successfully" , firstOption);
    }

    @Transactional
    public ApiResponse getDailyMealPlan() {
        Trainee trainee = ContextHolderUtils.getTrainee();
        LocalDate today = LocalDate.now();

        // Step 1: Try to get today's plan from DB
        MealPlan mealPlan = mealPlanRepository.findByTraineeAndDate(trainee, today)
                .orElseGet(() -> {
                    ApiResponse response = generateMealPlan();
                    final MealPlan plan = MealPlanMapper.toMealPlan((MealPlanOptionDto) response.data(), trainee , today);
                    return mealPlanRepository.save(plan);
                });

        // Step 2: Map meals to DTOs
        List<MealDto> meals = mealPlan.getMeals().stream()
                .map(MealMapper::toDto)
                .toList();

        // Step 3: Create daily summary
        DailyTotalDto dailyTotal = new DailyTotalDto(
                mealPlan.getTotalCalories(),
                mealPlan.getTotalCarbs(),
                mealPlan.getTotalFat(),
                mealPlan.getTotalProtein()
        );

        // Step 4: Return the result
        return ApiResponse.success("Your Daily Meal Plan", new MealPlanOptionDto(dailyTotal, meals));
    }


    // ✅ Step 1: Call nutrition-predict API
    private NutritionResponse callNutritionPredictAPI(AIServicePredictRequest request) {
        ResponseEntity<List<NutritionResponse>> response = restTemplate.exchange(
                NUTRITION_PREDICT_URL,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {
                }
        );

        List<NutritionResponse> responses = response.getBody();

        if (!response.getStatusCode().is2xxSuccessful() || responses == null || responses.isEmpty()) {
            throw new IllegalStateException("Failed to get nutrition prediction from API.");
        }
        return responses.get(0);
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

        MealPlanResponse body = response.getBody();

        if (!response.getStatusCode().is2xxSuccessful() || body == null || body.options() == null || body.options().isEmpty()) {
            throw new IllegalStateException("Failed to retrieve a valid meal plan option from API.");
        }

        return body.options().get(0);
    }

    // ✅ Step 3: Save result to DB
    private void saveMealPlan(MealPlanOptionDto option, Long traineeId) {
        MealPlan mealPlan = MealPlan.builder()
                .totalCalories(option.daily_total().calories())
                .totalCarbs(option.daily_total().carbs())
                .totalProtein(option.daily_total().protein())
                .totalFat(option.daily_total().fat())
                .trainee(traineeRepository.findById(traineeId).orElseThrow())
                .date(LocalDate.now())
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