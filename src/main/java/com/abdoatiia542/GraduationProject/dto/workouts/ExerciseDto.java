package com.abdoatiia542.GraduationProject.dto.workouts;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExerciseDto {
    private Long id;
    private String exerciseName;
    private Integer setsCount;
    private String reps;
    private Integer restSeconds;
    private String notes;
    private Integer exerciseOrder;
    private String bodyFocus;
    private Integer caloriesBurned;
    private Integer totalCalories;
    private String imageUrl;
    private String videoUrl;
    private LocalDateTime createdAt;
    private boolean isCompleted;
}
