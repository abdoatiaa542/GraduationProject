package com.abdoatiia542.GraduationProject.service.workout;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;

public interface WorkoutService {
    ApiResponse getExercisesByBodyFocus(String bodyFocusName);
    ApiResponse getExercisesByTrainingLevel(String trainingLevel);

}
