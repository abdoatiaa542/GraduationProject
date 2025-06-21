package com.abdoatiia542.GraduationProject.dto.workouts;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExerciseDto {
    private Long id;
    private String exerciseName;
    private Integer setsCount;
    private String reps;
    private Integer restSeconds;
    private String notes;
    private Integer exerciseOrder;
    private LocalDateTime createdAt;
}