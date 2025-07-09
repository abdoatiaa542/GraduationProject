package com.abdoatiia542.GraduationProject.service.workout;

import com.abdoatiia542.GraduationProject.cloudnairy.CloudinaryService;
import com.abdoatiia542.GraduationProject.dto.ExerciseUpdateResponse;
import com.abdoatiia542.GraduationProject.dto.ExerciseUploadRequestDto;
import com.abdoatiia542.GraduationProject.dto.SimpleExerciseResponse;
import com.abdoatiia542.GraduationProject.dto.WorkoutUpdateResponse;
import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.dailyRoutine.ExerciseDto;
import com.abdoatiia542.GraduationProject.dto.workoutResponse.WorkoutMediaUpdateRequest;
import com.abdoatiia542.GraduationProject.dto.workoutResponse.WorkoutSessionDTO;
import com.abdoatiia542.GraduationProject.handler.ResourceNotFoundException;
import com.abdoatiia542.GraduationProject.mapper.ExerciseMapper;
import com.abdoatiia542.GraduationProject.mapper.WorkoutSessionMapper;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.enumerations.TrainingLevel;
import com.abdoatiia542.GraduationProject.model.exercises.BodyFocus;
import com.abdoatiia542.GraduationProject.model.exercises.Exercise;
import com.abdoatiia542.GraduationProject.model.exercises.WorkoutSessions;
import com.abdoatiia542.GraduationProject.repository.TraineeRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.BodyFocusRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.ExerciseRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.WorkoutSessionsRepository;
import com.abdoatiia542.GraduationProject.utils.context.ContextHolderUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImp implements WorkoutService {

    private final ExerciseRepository exerciseRepository;
    private final WorkoutSessionsRepository workoutSessionsRepository;
    private final BodyFocusRepository bodyFocusRepository;
    private final TraineeRepository traineeRepository;
    private final CloudinaryService cloudinaryService;


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


    public ApiResponse updateExerciseOnly(Integer id, ExerciseUploadRequestDto request) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Exercise not found with id: " + id));

        exercise.setId(id);





        try {
            if (request.getExerciseImage() != null && !request.getExerciseImage().isEmpty()) {
                Map uploadResult = cloudinaryService.upload(request.getExerciseImage());
                exercise.setImageLink(uploadResult.get("secure_url").toString());
            }

            if (request.getExerciseVideo() != null && !request.getExerciseVideo().isEmpty()) {
                Map uploadResult = cloudinaryService.upload(request.getExerciseVideo());
                exercise.setVideoLink(uploadResult.get("secure_url").toString());
            }

        } catch (IOException e) {
            return ApiResponse.of("Error while uploading exercise media: " + e.getMessage());
        }
        exerciseRepository.save(exercise);

        ExerciseUpdateResponse response = new ExerciseUpdateResponse(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                exercise.getReps(),
                exercise.getSets(),
                exercise.getDurationSeconds(),
                exercise.getDurationRestSeconds(),
                exercise.getCaloriesBurned(),
                exercise.getTotalCalories(),
                exercise.getImageLink(),
                exercise.getVideoLink(),
                exercise.getBodyFocuses().stream().map(BodyFocus::getName).collect(Collectors.toSet())
        );

        return ApiResponse.success("Exercise updated successfully", response);
    }

    @Transactional
    public ApiResponse updateWorkoutOnly(Integer id, WorkoutMediaUpdateRequest request) {
        WorkoutSessions workout = workoutSessionsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Workout not found with id: " + id));

        workout.setId(id);


        try {
            if (request.workoutImage() != null && !request.workoutImage().isEmpty()) {
                Map uploadResult = cloudinaryService.upload(request.workoutImage());
                workout.setImage(uploadResult.get("secure_url").toString());
            }
        } catch (IOException e) {
            return ApiResponse.of("Error while uploading media: " + e.getMessage());
        }

        workoutSessionsRepository.save(workout);

        List<SimpleExerciseResponse> exercises = workout.getExercises()
                .stream()
                .map(ex -> new SimpleExerciseResponse(ex.getId(), ex.getName()))
                .toList();

        WorkoutUpdateResponse response = new WorkoutUpdateResponse(
                workout.getId(),
                workout.getName(),
                workout.getDescription(),
                workout.getTrainingLevel().name(),
                workout.getImage(),
                exercises
        );

        return ApiResponse.success("Workout updated successfully", response);
    }


    @Override
    public ApiResponse getExercisesById(Integer id) {
        Exercise exerciseOptional = exerciseRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Workout not found with id: " + id));

        return ApiResponse.of("exercise response: ", exerciseOptional);
    }
}
