package com.abdoatiia542.GraduationProject.controller.workouts;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.workouts.ExerciseDto;
import com.abdoatiia542.GraduationProject.model.workout.TrainingLevel;
import com.abdoatiia542.GraduationProject.service.workout.ExerciseService;
import com.abdoatiia542.GraduationProject.service.workout.WorkoutPlanService;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workout-plans")
@Validated
@Slf4j
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;
    private final ExerciseService exerciseService;

    public WorkoutPlanController(WorkoutPlanService workoutPlanService, ExerciseService exerciseService) {
        this.workoutPlanService = workoutPlanService;
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public ResponseEntity<?> getAllWorkoutPlans() {
        return ResponseEntity.ok(workoutPlanService.getAllWorkoutPlans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkoutPlanById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(workoutPlanService.getWorkoutPlanById(id));
    }

    private ResponseEntity<ApiResponse> toResponse(String message, List<ExerciseDto> data) {
        return data.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure("No exercises found for the specified training level"))
                : ResponseEntity.ok(ApiResponse.success(message, data));
    }

    @GetMapping("/WorkoutByLevel")
    public ResponseEntity<ApiResponse> getRandom8ByTrainingLevel(@RequestParam TrainingLevel level) {
        return toResponse("Fetched 8 random exercises", exerciseService.getRandomExercisesByLevel(level));
    }

}
