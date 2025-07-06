package com.abdoatiia542.GraduationProject.controller.traineeworkout;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.service.workout.WorkoutService;
import com.abdoatiia542.GraduationProject.utils.Response.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/user/workouts")
@AllArgsConstructor
public class TraineeWorkoutController {

    private final WorkoutService workoutService;

    @PostMapping("/{workoutId}")
    public ResponseEntity<?> saveWorkout(@PathVariable Integer workoutId) {
        ApiResponse response = workoutService.saveWorkoutForTrainee(workoutId);
        return ResponseUtil.okOrNotFound(response);
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<?> unSaveWorkout(@PathVariable Integer workoutId) {
        ApiResponse response = workoutService.unSaveWorkoutForTrainee(workoutId);
        return ResponseUtil.okOrNotFound(response);
    }
}
