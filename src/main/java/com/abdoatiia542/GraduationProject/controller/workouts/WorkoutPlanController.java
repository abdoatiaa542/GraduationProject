package com.abdoatiia542.GraduationProject.controller.workouts;

import com.abdoatiia542.GraduationProject.service.workout.WorkoutPlanService;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/workout-plans")
//@CrossOrigin(origins = "*")
@Validated
@Slf4j
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;

    public WorkoutPlanController(WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @GetMapping
    public ResponseEntity<?> getAllWorkoutPlans() {
        return ResponseEntity.ok(workoutPlanService.getAllWorkoutPlans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkoutPlanById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(workoutPlanService.getWorkoutPlanById(id));
    }
}
