package com.abdoatiia542.GraduationProject.controller.ai;


import com.abdoatiia542.GraduationProject.dto.ai.NutritionPredictRequest;
import com.abdoatiia542.GraduationProject.service.ai.MealPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meal-plan")
public class MealPlanController {

    private final MealPlanService mealPlanService;

    @PostMapping("/generate/{traineeId}")
    public ResponseEntity<?> generateMealPlan(
            @RequestBody NutritionPredictRequest request,
            @PathVariable Long traineeId
    ) {
        return ResponseEntity.ok().body(mealPlanService.generateMealPlan(request, traineeId));
    }
}
