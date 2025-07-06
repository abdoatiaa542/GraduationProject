package com.abdoatiia542.GraduationProject.controller.plan;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.service.ai.MealPlanService;
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

    private final TraineeService traineeService;
    private final MealPlanService mealPlanService;

    // ده بيعمل GENERATE للاتنين بالله متبقاش فلاح انت وهو
    @PostMapping("/generate")
    public ResponseEntity<?> generatePlan() {
        final ApiResponse response = traineeService.generatePlanForTrainee();
        // المفروض تكلم ال api service هنا
        /// mealPlanService.generateMealPlan(response.getData());
        return  ResponseUtil.okOrBadRequest(response);
    }
}
