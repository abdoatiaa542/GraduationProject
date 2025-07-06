package com.abdoatiia542.GraduationProject.controller.dailyRoutine;


import com.abdoatiia542.GraduationProject.service.ai.MealPlanService;
import com.abdoatiia542.GraduationProject.service.dailyRoutine.DailyRoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daily-plan")
public class DailyRoutineController {

    private final MealPlanService mealPlanService;
    private final DailyRoutineService dailyRoutineService;

//    @PostMapping("/meal")
//    public ResponseEntity<?> generateMealPlan(@RequestBody NutritionPredictRequest request) {
//        return ResponseEntity.ok().body(mealPlanService.generateMealPlan(request));
//    }

    @GetMapping("/meal")
    public ResponseEntity<?> generateMealPlan() {
        return ResponseEntity.ok().body(mealPlanService.generateMealPlan());
    }

    @GetMapping("/workout")
    public ResponseEntity<?> generateExercisePlan() {
        return ResponseEntity.ok().body(dailyRoutineService.getDailyWorkout());
    }

}
