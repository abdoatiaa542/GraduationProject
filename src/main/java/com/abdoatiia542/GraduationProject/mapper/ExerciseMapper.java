package com.abdoatiia542.GraduationProject.mapper;

import com.abdoatiia542.GraduationProject.dto.dailyRoutine.ExerciseDto;
import com.abdoatiia542.GraduationProject.model.exercises.Exercise;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ExerciseMapper {

    public static ExerciseDto toDto(Exercise exercise) {
        return ExerciseDto.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .description(exercise.getDescription())
                .imageLink(exercise.getImageLink())
                .videoLink(exercise.getVideoLink())
                .reps(exercise.getReps())
                .sets(exercise.getSets())
                .durationSeconds(exercise.getDurationSeconds())
                .caloriesBurned(exercise.getCaloriesBurned())
                .totalCalories(exercise.getTotalCalories())
                .durationRestSeconds(exercise.getDurationRestSeconds())
                .bodyFocuses(
                        exercise.getBodyFocuses() != null
                                ? exercise.getBodyFocuses()
                                .stream()
                                .map(bodyFocus -> bodyFocus.getName())
                                .collect(Collectors.toSet())
                                : null
                )
                .build();
    }
}
