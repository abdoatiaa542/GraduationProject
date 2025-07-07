package com.abdoatiia542.GraduationProject.service.workout;

import com.abdoatiia542.GraduationProject.dto.ExerciseUploadRequestDto;
import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.workoutResponse.WorkoutMediaUpdateRequest;

public interface WorkoutService {
    ApiResponse getExercisesByBodyFocus(String bodyFocusName);

    ApiResponse getWorkoutsByTrainingLevel(String trainingLevel);

    ApiResponse getBodyFocuses();

    ApiResponse getRecommendedWorkouts();

    ApiResponse getSavedWorkouts();

    ApiResponse saveWorkoutForTrainee(Integer workoutId);

    ApiResponse unSaveWorkoutForTrainee(Integer workoutId);

//    ApiResponse updateWorkoutMedia(Integer id, WorkoutMediaUpdateRequest data);

    ApiResponse updateWorkoutOnly(Integer id, WorkoutMediaUpdateRequest data);

    ApiResponse updateExerciseOnly(Integer id, ExerciseUploadRequestDto exerciseUploadRequestDto);
}
