package com.abdoatiia542.GraduationProject.mapper;

import com.abdoatiia542.GraduationProject.dto.workouts.ExerciseDto;
import com.abdoatiia542.GraduationProject.dto.workouts.PlanConsiderationDto;
import com.abdoatiia542.GraduationProject.dto.workouts.WorkoutDayDto;
import com.abdoatiia542.GraduationProject.dto.workouts.WorkoutPlanDto;
import com.abdoatiia542.GraduationProject.model.workout.Exercise;
import com.abdoatiia542.GraduationProject.model.workout.PlanConsideration;
import com.abdoatiia542.GraduationProject.model.workout.WorkoutDay;
import com.abdoatiia542.GraduationProject.model.workout.WorkoutPlan;

import java.util.stream.Collectors;

public class WorkoutPlanMapper {

    public static WorkoutPlanDto toDto(WorkoutPlan entity) {
        WorkoutPlanDto dto = new WorkoutPlanDto();
        dto.setId(entity.getId());
        dto.setBmiLevel(entity.getBmiLevel());
        dto.setTrainingSplit(entity.getTrainingSplit());
        dto.setGoal(entity.getGoal());
        dto.setTrainingLevel(entity.getTrainingLevel());
        dto.setColorGradient(entity.getColorGradient());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        if (entity.getWorkoutDays() != null) {
            dto.setWorkoutDays(entity.getWorkoutDays().stream()
                    .map(WorkoutPlanMapper::toDto)
                    .collect(Collectors.toList()));
        }

        if (entity.getConsiderations() != null) {
            dto.setConsiderations(entity.getConsiderations().stream()
                    .map(WorkoutPlanMapper::toDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public static WorkoutDayDto toDto(WorkoutDay entity) {
        WorkoutDayDto dto = new WorkoutDayDto();
        dto.setId(entity.getId());
        dto.setDayNumber(entity.getDayNumber());
        dto.setDayName(entity.getDayName());
        dto.setIsRestDay(entity.getIsRestDay());
        dto.setRestDayNotes(entity.getRestDayNotes());
        dto.setCreatedAt(entity.getCreatedAt());

        if (entity.getExercises() != null) {
            dto.setExercises(entity.getExercises().stream()
                    .map(WorkoutPlanMapper::toDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
    public static ExerciseDto toDto(Exercise entity) {
        return ExerciseDto.builder()
                .id(entity.getId())
                .exerciseName(entity.getExerciseName())
                .setsCount(entity.getSetsCount())
                .reps(entity.getReps())
                .restSeconds(entity.getRestSeconds())
                .notes(entity.getNotes())
                .exerciseOrder(entity.getExerciseOrder())
                .bodyFocus(entity.getBodyFocus())
                .caloriesBurned(entity.getCaloriesBurned())
                .totalCalories(entity.getTotalCalories())
                .imageUrl(entity.getImageUrl())
                .videoUrl(entity.getVideoUrl())
                .createdAt(entity.getCreatedAt())
                .isCompleted(entity.getIsCompleted() != null ? entity.getIsCompleted() : false)
                .build();
    }


    public static PlanConsiderationDto toDto(PlanConsideration entity) {
        PlanConsiderationDto dto = new PlanConsiderationDto();
        dto.setId(entity.getId());
        dto.setConsiderationText(entity.getConsiderationText());
        return dto;
    }
}
