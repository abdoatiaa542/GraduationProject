package com.abdoatiia542.GraduationProject.controller.discover;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.service.workout.WorkoutService;
import com.abdoatiia542.GraduationProject.utils.Response.ResponseUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping(value = "api/v1/discover")
@AllArgsConstructor
public class DiscoverController {

    private final WorkoutService workoutService;

    @GetMapping("/exercises")
    public ResponseEntity<?> getExercisesByBodyFocus(@RequestParam @NotBlank String bodyFocus) {
        ApiResponse response = workoutService.getExercisesByBodyFocus(bodyFocus);
        return ResponseUtil.okOrNotFound(response);
    }

    @GetMapping("/body-focuses")
    public ResponseEntity<?> getBodyFocuses() {
        ApiResponse response = workoutService.getBodyFocuses();
        return ResponseUtil.okOrNotFound(response);
    }

    @GetMapping("/workouts")
    public ResponseEntity<?> getWorkouts(@RequestParam @NotBlank String level) {
        ApiResponse response = workoutService.getWorkoutsByTrainingLevel(level);
        return ResponseUtil.okOrNotFound(response);
    }

    @GetMapping("/recommended")
    public ResponseEntity<?> getRecommendedWorkouts() {
        ApiResponse response = workoutService.getRecommendedWorkouts();
        return ResponseUtil.okOrNotFound(response);
    }

    @GetMapping("/saved")
    public ResponseEntity<?> getSavedWorkouts() {
        final ApiResponse response = workoutService.getSavedWorkouts();
        return ResponseUtil.okOrNotFound(response);
    }


}
