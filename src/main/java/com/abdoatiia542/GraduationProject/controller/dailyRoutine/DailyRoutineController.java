package com.abdoatiia542.GraduationProject.controller.dailyRoutine;


import com.abdoatiia542.GraduationProject.service.ai.MealPlanService;
import com.abdoatiia542.GraduationProject.service.dailyRoutine.WorkoutPlanService;
import com.abdoatiia542.GraduationProject.utils.Response.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/daily-plan")
public class DailyRoutineController {

    private final MealPlanService mealPlanService;
    private final WorkoutPlanService dailyRoutineService;



    @GetMapping("/meal")
    public ResponseEntity<?> generateMealPlan() {
        return ResponseUtil.okOrBadRequest(mealPlanService.getDailyMealPlan());
    }

    @GetMapping("/workout")
    public ResponseEntity<?> generateExercisePlan() {
        return ResponseEntity.ok().body(dailyRoutineService.getDailyWorkout());
    }

}
