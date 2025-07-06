package com.abdoatiia542.GraduationProject.mapper;

import com.abdoatiia542.GraduationProject.dto.dailyRoutine.ExerciseDto;
import com.abdoatiia542.GraduationProject.dto.workoutResponse.WorkoutSessionDTO;
import com.abdoatiia542.GraduationProject.model.exercises.WorkoutSessions;

import java.util.List;
import java.util.stream.Collectors;

public class WorkoutSessionMapper {

    public static List<WorkoutSessionDTO> mapToDtoList(List<WorkoutSessions> workoutSessions) {
        return workoutSessions.stream()
                .map(ws -> WorkoutSessionDTO.builder()
                        .id(ws.getId())
                        .name(ws.getName())
                        .image(ws.getImage())
                        .description(ws.getDescription())
                        .trainingLevel(ws.getTrainingLevel().name())
                        .exercises(ws.getExercises().stream()
                                .map(ex -> ExerciseDto.builder()
                                        .id(ex.getId())
                                        .name(ex.getName())
                                        .description(ex.getDescription())
                                        .imageLink(ex.getImageLink())
                                        .videoLink(ex.getVideoLink())
                                        .reps(ex.getReps())
                                        .sets(ex.getSets())
                                        .durationSeconds(ex.getDurationSeconds())
                                        .durationRestSeconds(ex.getDurationRestSeconds())
                                        .caloriesBurned(ex.getCaloriesBurned())
                                        .totalCalories(ex.getTotalCalories())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }


    public static WorkoutSessionDTO toDto(WorkoutSessions entity) {
        if (entity == null) return null;

        WorkoutSessionDTO dto = new WorkoutSessionDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setImage(entity.getImage());
        dto.setTrainingLevel(entity.getTrainingLevel().name());
        dto.setDescription(entity.getDescription());

        if (entity.getExercises() != null) {
            dto.setExercises(entity.getExercises().stream()
                    .map(ExerciseMapper::toDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}