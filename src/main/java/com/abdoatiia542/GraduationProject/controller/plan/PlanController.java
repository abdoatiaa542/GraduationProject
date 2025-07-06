package com.abdoatiia542.GraduationProject.controller.plan;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.service.ai.MealPlanService;
import com.abdoatiia542.GraduationProject.service.dailyRoutine.WorkoutPlanService;
import com.abdoatiia542.GraduationProject.service.trainee.TraineeService;
import com.abdoatiia542.GraduationProject.utils.Response.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/plan")
@AllArgsConstructor
public class PlanController {

    private final WorkoutPlanService workoutPlanService;
    private final MealPlanService mealPlanService;

    @PostMapping("/generate")
    public ResponseEntity<?> generatePlan() {
        final ApiResponse workoutResponse = workoutPlanService.generatePlanForTrainee();
        final ApiResponse mealResponse = mealPlanService.generateMealPlan();

        if(workoutResponse.success() && mealResponse.success()) {
            return ResponseEntity.ok(ApiResponse.success("Plan Generated Successfully"));
        }
        return  ResponseEntity.badRequest().body(ApiResponse.failure("Failed to generate plan"));
    }
}
