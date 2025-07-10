package com.abdoatiia542.GraduationProject.dto.workoutResponse;

import com.abdoatiia542.GraduationProject.dto.dailyRoutine.ExerciseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutSessionDTO {
    private Integer id;
    private String name;
    private String image;
    private String description;
    private String trainingLevel;
    private List<ExerciseDto> exercises;
    private Boolean isSaved;
}
