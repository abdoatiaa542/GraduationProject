package com.abdoatiia542.GraduationProject.controller.workout;
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
    @RequestMapping(value = "api/v1/workout")
    @AllArgsConstructor
    public class WorkoutController {

        private final WorkoutService workoutService;

        @GetMapping("/exercisesByBodyFocus")
        public ResponseEntity<?> getExercisesByBodyFocus(@RequestParam @NotBlank String bodyFocus) {
            ApiResponse response = workoutService.getExercisesByBodyFocus(bodyFocus);
            return ResponseUtil.okOrNotFound(response);
        }
        @GetMapping("/exercisesByTrainingLevel")
        public ResponseEntity<?> getExercisesByTrainingLevel(@RequestParam @NotBlank String level) {
         ApiResponse response = workoutService.getExercisesByTrainingLevel(level);
         return ResponseUtil.okOrNotFound(response);
        }


}
