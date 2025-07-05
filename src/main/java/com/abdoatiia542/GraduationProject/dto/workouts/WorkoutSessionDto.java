package com.abdoatiia542.GraduationProject.dto.workouts;

import com.abdoatiia542.GraduationProject.model.enumerations.TrainingLevel;
import lombok.Data;

import java.util.List;

@Data
public class WorkoutSessionDto {
    private Integer id;
    private String name;
    private String image;
    private TrainingLevel trainingLevel;
    private String description;
    private List<ExerciseDto> exercises;
}
