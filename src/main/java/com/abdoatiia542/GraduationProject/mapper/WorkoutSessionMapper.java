package com.abdoatiia542.GraduationProject.mapper;

import com.abdoatiia542.GraduationProject.dto.dailyRoutine.ExerciseDto;
import com.abdoatiia542.GraduationProject.dto.dailyRoutine.WorkoutSessionDto;
import com.abdoatiia542.GraduationProject.model.exercises.WorkoutSessions;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class WorkoutSessionMapper {

    public List<WorkoutSessionDto> mapToDtoList(List<WorkoutSessions> workoutSessions) {
        return workoutSessions.stream()
                .map(ws -> WorkoutSessionDto.builder()
                        .id(ws.getId())
                        .name(ws.getName())
                        .image(ws.getImage())
                        .description(ws.getDescription())
                        .trainingLevel(ws.getTrainingLevel())
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
}