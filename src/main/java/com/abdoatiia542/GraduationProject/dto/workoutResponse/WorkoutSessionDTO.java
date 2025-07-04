package com.abdoatiia542.GraduationProject.dto.workoutResponse;

import lombok.Data;
import java.util.List;

@Data
public class WorkoutSessionDTO {
    private Integer id;
    private String name;
    private String image;
    private String description;
    private String trainingLevel;
    private String imageLink;
    private List<ExerciseDTO> exercises;
}
