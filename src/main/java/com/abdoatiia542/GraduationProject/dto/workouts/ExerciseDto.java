package com.abdoatiia542.GraduationProject.dto.workouts;

import com.abdoatiia542.GraduationProject.model.plan.BodyFocus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<BodyFocus> bodyFocuses;
    private Integer caloriesBurned;
    private Integer totalCalories;
    private String imageUrl;
    private String videoUrl;
    private LocalDateTime createdAt;
    private boolean isCompleted;
}
