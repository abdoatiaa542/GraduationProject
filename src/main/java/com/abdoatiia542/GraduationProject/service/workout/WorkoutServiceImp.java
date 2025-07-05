package com.abdoatiia542.GraduationProject.service.workout;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.workouts.ExerciseDto;
import com.abdoatiia542.GraduationProject.dto.workouts.WorkoutSessionDto;
import com.abdoatiia542.GraduationProject.mapper.ExerciseMapper;
import com.abdoatiia542.GraduationProject.mapper.WorkoutSessionMapper;
import com.abdoatiia542.GraduationProject.model.enumerations.TrainingLevel;
import com.abdoatiia542.GraduationProject.model.plan.BodyFocus;
import com.abdoatiia542.GraduationProject.model.plan.Exercise;
import com.abdoatiia542.GraduationProject.model.plan.WorkoutSessions;
import com.abdoatiia542.GraduationProject.repository.workouts.BodyFocusRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.ExerciseRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.WorkoutSessionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImp implements WorkoutService {

    private final ExerciseRepository exerciseRepository;
    private final WorkoutSessionsRepository workoutSessionsRepository;
    private final BodyFocusRepository bodyFocusRepository;


    public ApiResponse getExercisesByBodyFocus(String bodyFocusName) {
        List<ExerciseDto> exercises = exerciseRepository
                .findByBodyFocuses(bodyFocusName)
                .stream()
                .map(ExerciseMapper::toDto)
                .toList();

        if (exercises.isEmpty()) {
            return ApiResponse.failure("No exercises found for body focus: " + bodyFocusName);
        }

        return ApiResponse.success("Exercises fetched successfully", exercises);
    }

    public ApiResponse getWorkoutsByTrainingLevel(String level) {
        List<WorkoutSessionDto> exercises = workoutSessionsRepository.findByTrainingLevel(TrainingLevel.from(level.toUpperCase())).stream()
                .map(WorkoutSessionMapper::toDto)
                .distinct() //
                .toList();

        if (exercises.isEmpty()) {
            return ApiResponse.failure("No exercises found for training level: " + level);
        }

        return ApiResponse.success("Exercises fetched successfully", exercises);
    }

    @Override
    public ApiResponse getBodyFocuses() {
        final List<String> bodyFocuses = bodyFocusRepository.findAll().stream().map(BodyFocus::getName).toList();

        if (bodyFocuses.isEmpty()) {
            return ApiResponse.failure("No body focuses found");
        }

        return ApiResponse.success("Body focuses fetched successfully", bodyFocuses);
    }

    @Override
    public ApiResponse getRecommendedWorkouts() {
        List<WorkoutSessions> allSessions = workoutSessionsRepository.findAll();

        Collections.shuffle(allSessions);
        List<WorkoutSessionDto> recommended = allSessions.stream()
                .limit(6)
                .map(WorkoutSessionMapper::toDto)
                .toList();

        return ApiResponse.success("Recommended sessions fetched successfully", recommended);
    }


}
