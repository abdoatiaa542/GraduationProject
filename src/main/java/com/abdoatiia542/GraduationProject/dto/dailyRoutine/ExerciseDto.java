package com.abdoatiia542.GraduationProject.dto.dailyRoutine;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class ExerciseDto {
    private Integer id;
    private String name;
    private String description;
    private String imageLink;
    private String videoLink;
    private String reps;
    private Integer sets;
    private Integer durationSeconds;
    private Integer durationRestSeconds;
    private Integer caloriesBurned;
    private Integer totalCalories;
    private Set<String> bodyFocuses;
}