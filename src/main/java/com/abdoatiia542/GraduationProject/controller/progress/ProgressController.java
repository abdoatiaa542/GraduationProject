package com.abdoatiia542.GraduationProject.controller.progress;

import com.abdoatiia542.GraduationProject.dto.progress.DailyProgressDto;
import com.abdoatiia542.GraduationProject.service.progress.DailyProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final DailyProgressService progressService;


    @PostMapping("/exercise/complete")
    public ResponseEntity<Map<String, Object>> recordExerciseCompletion(@RequestParam Integer exerciseId) {
        int totalBurned = progressService.completeExerciseAndTrackProgress(exerciseId);
        return ResponseEntity.ok(
                Map.of("message", "✅ Exercise progress recorded for today.",
                        "burnedCalories", totalBurned)
        );
    }

    @GetMapping
    public ResponseEntity<DailyProgressDto> getProgressByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        DailyProgressDto dto = progressService.getProgressByDate(date);
        return ResponseEntity.ok(dto);
    }


    @PostMapping("/workout/complete")
    public ResponseEntity<Map<String, Object>> recordWorkoutCompletion(@RequestParam Integer sessionId) {
        int totalBurned = progressService.completeWorkoutSessionAndTrackProgress(sessionId);
        return ResponseEntity.ok(
                Map.of(
                        "message", "✅ Workout session progress recorded for today.",
                        "burnedCalories", totalBurned
                )
        );
    }
}
