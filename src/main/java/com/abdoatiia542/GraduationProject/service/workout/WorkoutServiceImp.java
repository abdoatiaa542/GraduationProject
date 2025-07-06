package com.abdoatiia542.GraduationProject.service.workout;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.dailyRoutine.ExerciseDto;
import com.abdoatiia542.GraduationProject.dto.workoutResponse.WorkoutSessionDTO;
import com.abdoatiia542.GraduationProject.handler.ResourceNotFoundException;
import com.abdoatiia542.GraduationProject.mapper.ExerciseMapper;
import com.abdoatiia542.GraduationProject.mapper.WorkoutSessionMapper;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.enumerations.TrainingLevel;
import com.abdoatiia542.GraduationProject.model.exercises.BodyFocus;
import com.abdoatiia542.GraduationProject.model.exercises.WorkoutSessions;
import com.abdoatiia542.GraduationProject.repository.TraineeRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.BodyFocusRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.ExerciseRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.WorkoutSessionsRepository;
import com.abdoatiia542.GraduationProject.utils.context.ContextHolderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImp implements WorkoutService {

    private final ExerciseRepository exerciseRepository;
    private final WorkoutSessionsRepository workoutSessionsRepository;
    private final BodyFocusRepository bodyFocusRepository;
    private final TraineeRepository traineeRepository;


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
        List<WorkoutSessionDTO> exercises = workoutSessionsRepository.findByTrainingLevel(TrainingLevel.from(level.toUpperCase())).stream()
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
        List<WorkoutSessionDTO> recommended = allSessions.stream()
                .limit(6)
                .map(WorkoutSessionMapper::toDto)
                .toList();

        return ApiResponse.success("Recommended sessions fetched successfully", recommended);
    }

    public ApiResponse getSavedWorkouts() {
        Trainee trainee = traineeRepository.findByIdWithSavedWorkouts(ContextHolderUtils.getTrainee().getId());

        final List<WorkoutSessionDTO> savedWorkouts = trainee
                .getSavedWorkouts()
                .stream()
                .map(WorkoutSessionMapper::toDto)
                .toList();
        return ApiResponse.success("Saved Workouts fetched successfully", savedWorkouts);
    }

    @Override
    public ApiResponse saveWorkoutForTrainee(Integer workoutId) {
        Trainee trainee = traineeRepository.findByIdWithSavedWorkouts(ContextHolderUtils.getTrainee().getId());

        WorkoutSessions workout = workoutSessionsRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found with id: " + workoutId));

        List<WorkoutSessions> savedWorkouts = trainee.getSavedWorkouts();

        if (savedWorkouts.contains(workout)) {
            return ApiResponse.failure("Workout already saved");
        }

        trainee.getSavedWorkouts().add(workout);
        traineeRepository.save(trainee);

        return ApiResponse.success("Workout saved successfully", null);
    }

    @Override
    public ApiResponse unSaveWorkoutForTrainee(Integer workoutId) {
        Trainee trainee = traineeRepository.findByIdWithSavedWorkouts(ContextHolderUtils.getTrainee().getId());

        WorkoutSessions workout = workoutSessionsRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found with id: " + workoutId));

        List<WorkoutSessions> savedWorkouts = trainee.getSavedWorkouts();

        if (savedWorkouts.contains(workout)) {
            return ApiResponse.failure("Workout was not in saved list");
        }

        trainee.getSavedWorkouts().removeIf(w -> w.getId().equals(workoutId));
        traineeRepository.save(trainee);

        return ApiResponse.success("Workout removed from saved workouts", null);
    }


}
