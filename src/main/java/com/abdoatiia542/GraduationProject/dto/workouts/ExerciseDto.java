package com.abdoatiia542.GraduationProject.dto.workouts;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ExerciseDto {

    private Integer id;
    private String exerciseName;
    private String description;
    private String imageLink;
    private String videoLink;
    private String reps;
    private Integer sets;
    private Integer durationSeconds;
    private Integer caloriesBurned;
    private Integer totalCalories;
    private Integer durationRestSeconds;
    private Set<String> bodyFocuses;
}
