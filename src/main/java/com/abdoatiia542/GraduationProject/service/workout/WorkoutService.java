package com.abdoatiia542.GraduationProject.service.workout;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.model.plan.Exercise;

import java.util.List;

public interface WorkoutService {
    ApiResponse getExercisesByBodyFocus(String bodyFocusName);
    ApiResponse getExercisesByTrainingLevel(String trainingLevel);

}
