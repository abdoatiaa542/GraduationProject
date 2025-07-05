package com.abdoatiia542.GraduationProject.service.workout;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.model.enumerations.TrainingLevel;

public interface WorkoutService {
    ApiResponse getExercisesByBodyFocus(String bodyFocusName);
    ApiResponse getWorkoutsByTrainingLevel(String trainingLevel);
    ApiResponse getBodyFocuses();
    ApiResponse getRecommendedWorkouts();

}
