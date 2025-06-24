package com.abdoatiia542.GraduationProject.controller.workouts;

import com.abdoatiia542.GraduationProject.dto.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.workouts.ExerciseDto;
import com.abdoatiia542.GraduationProject.model.workout.TrainingLevel;
import com.abdoatiia542.GraduationProject.service.workout.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workouts/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;
    @GetMapping("/WorkoutByLevel")
    public ResponseEntity<ApiResponse> getRandom8ByTrainingLevel(@RequestParam TrainingLevel level) {
        List<ExerciseDto> exercises = exerciseService.getRandom8ByTrainingLevel(level);

        if (exercises.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.failure("No exercises found for the specified training level"));
        }

        return ResponseEntity.ok(
                ApiResponse.success("Fetched 8 random exercises", exercises)
        );
    }

}
