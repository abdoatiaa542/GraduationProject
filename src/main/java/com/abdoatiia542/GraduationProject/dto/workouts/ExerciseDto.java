package com.abdoatiia542.GraduationProject.dto.workouts;

import com.abdoatiia542.GraduationProject.model.enumerations.BodyFocus;
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
    private BodyFocus bodyFocus;
    private Integer caloriesBurned;
    private Integer totalCalories;
    private String imageUrl;
    private String videoUrl;
    private LocalDateTime createdAt;
    private boolean isCompleted;
}
