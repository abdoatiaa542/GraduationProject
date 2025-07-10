package com.abdoatiia542.GraduationProject.mapper;

import com.abdoatiia542.GraduationProject.dto.dailyRoutine.ExerciseDto;
import com.abdoatiia542.GraduationProject.dto.workoutResponse.WorkoutSessionDTO;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.exercises.WorkoutSessions;
import com.abdoatiia542.GraduationProject.repository.TraineeRepository;
import com.abdoatiia542.GraduationProject.utils.context.ContextHolderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Component
public class WorkoutSessionMapper {

    @Autowired
    private TraineeRepository traineeRepository;


    private  boolean isSaved(WorkoutSessions entity) {
        Trainee trainee = traineeRepository.findByIdWithSavedWorkouts(ContextHolderUtils.getTrainee().getId());
        return trainee.getSavedWorkouts().stream().anyMatch(e -> Objects.equals(entity.getId(), e.getId()));
    }

    public List<WorkoutSessionDTO> mapToDtoList(List<WorkoutSessions> workoutSessions) {

        return workoutSessions.stream()
                .map(ws -> WorkoutSessionDTO.builder()
                        .id(ws.getId())
                        .name(ws.getName())
                        .image(ws.getImage())
                        .description(ws.getDescription())
                        .trainingLevel(ws.getTrainingLevel().name())
                        .isSaved(isSaved(ws))
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


    public WorkoutSessionDTO toDto(WorkoutSessions entity) {
        if (entity == null) return null;

        WorkoutSessionDTO dto = new WorkoutSessionDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setImage(entity.getImage());
        dto.setTrainingLevel(entity.getTrainingLevel().name());
        dto.setDescription(entity.getDescription());
        dto.setIsSaved(isSaved(entity));

        if (entity.getExercises() != null) {
            dto.setExercises(entity.getExercises().stream()
                    .map(ExerciseMapper::toDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}