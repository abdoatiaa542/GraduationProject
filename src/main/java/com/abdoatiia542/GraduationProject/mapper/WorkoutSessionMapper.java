package com.abdoatiia542.GraduationProject.mapper;

import com.abdoatiia542.GraduationProject.dto.workouts.WorkoutSessionDto;
import com.abdoatiia542.GraduationProject.model.plan.Exercise;
import com.abdoatiia542.GraduationProject.model.plan.WorkoutSessions;

import java.util.stream.Collectors;

public class WorkoutSessionMapper {

    public static WorkoutSessionDto toDto(WorkoutSessions entity) {
        if (entity == null) return null;

        WorkoutSessionDto dto = new WorkoutSessionDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setImage(entity.getImage());
        dto.setTrainingLevel(entity.getTrainingLevel());
        dto.setDescription(entity.getDescription());

        if (entity.getExercises() != null) {
            dto.setExercises(entity.getExercises().stream()
                    .map(ExerciseMapper::toDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }


}
