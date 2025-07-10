package com.abdoatiia542.GraduationProject.controller.progress;

import com.abdoatiia542.GraduationProject.dto.progress.DailyProgressDto;
import com.abdoatiia542.GraduationProject.service.progress.DailyProgressService;
import com.abdoatiia542.GraduationProject.utils.Response.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final DailyProgressService progressService;


    @PostMapping("/exercise/complete")
    public ResponseEntity<?> recordExerciseCompletion(@RequestParam Integer exerciseId, @RequestParam Integer exerciseDuration) {
        return ResponseUtil.okOrNotFound( progressService.completeExerciseAndTrackProgress(exerciseId, exerciseDuration));
    }

    @GetMapping
    public ResponseEntity<?> getProgressByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseUtil.okOrNotFound(progressService.getProgressByDate(date));

    }


    @PostMapping("/workout/complete")
    public ResponseEntity<?> recordWorkoutCompletion(@RequestParam Integer sessionId, @RequestParam Integer exerciseDuration) {
        return ResponseUtil.okOrNotFound(progressService.completeWorkoutSessionAndTrackProgress(sessionId , exerciseDuration));
    }
    @PostMapping("/meal/complete")
    public ResponseEntity<?> recordNutrition(@RequestParam Long id) {
        return ResponseUtil.okOrNotFound(progressService.completeMeal(id));
    }
}
