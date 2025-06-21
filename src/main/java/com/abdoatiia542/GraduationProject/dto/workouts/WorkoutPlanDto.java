package com.abdoatiia542.GraduationProject.dto.workouts;

import com.abdoatiia542.GraduationProject.model.workout.TrainingLevel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkoutPlanDto {
    private Long id;
    private Integer bmiLevel;
    private String trainingSplit;
    private String goal;
    private TrainingLevel trainingLevel;
    private String colorGradient;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<WorkoutDayDto> workoutDays;
    private List<PlanConsiderationDto> considerations;
}