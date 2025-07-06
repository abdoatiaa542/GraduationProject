package com.abdoatiia542.GraduationProject.dto.dailyRoutine;

import com.abdoatiia542.GraduationProject.model.enumerations.TrainingLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutSessionDto {
    private Integer id;
    private String name;
    private String image;
    private TrainingLevel trainingLevel;
    private String description;
    private List<ExerciseDto> exercises;
}