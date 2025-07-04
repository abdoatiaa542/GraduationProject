package com.abdoatiia542.GraduationProject.dto.workoutResponse;

import lombok.Data;

@Data
public class ExerciseDTO {
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
}
