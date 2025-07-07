package com.abdoatiia542.GraduationProject.dto.dailyRoutine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseUploadDto {
    private Integer id;
    private String name;
    private String description;
    private String reps;
    private Integer sets;
    private Integer durationSeconds;
    private Integer durationRestSeconds;
    private Integer caloriesBurned;
    private Integer totalCalories;
    private Set<String> bodyFocuses;
}