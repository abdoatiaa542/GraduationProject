package com.abdoatiia542.GraduationProject.controller.traineeworkout;

import com.abdoatiia542.GraduationProject.dto.ExerciseUploadRequestDto;
import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.workoutResponse.WorkoutMediaUpdateRequest;
import com.abdoatiia542.GraduationProject.service.workout.WorkoutService;
import com.abdoatiia542.GraduationProject.utils.Response.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
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

    @GetMapping("get exercises by id/{id}")
    public ResponseEntity<?> getExercisesByWorkoutId(@PathVariable Integer id) {
        return ResponseEntity.ok().body(workoutService.getExercisesById(id));
    }


    @PutMapping(value = "/{id}/upload-media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateWorkoutMedia(
            @PathVariable Integer id,
            @ModelAttribute WorkoutMediaUpdateRequest data
    ) {
        return ResponseEntity.ok().body(workoutService.updateWorkoutOnly(id, data));
    }


    @PutMapping(value = "/exercises/{exerciseId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateExerciseOnly(
            @PathVariable Integer exerciseId,
            @ModelAttribute ExerciseUploadRequestDto request
    ) {
        return ResponseEntity.ok(workoutService.updateExerciseOnly(exerciseId, request));
    }

}
