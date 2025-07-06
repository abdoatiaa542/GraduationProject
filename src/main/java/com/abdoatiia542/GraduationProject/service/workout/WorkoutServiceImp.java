package com.abdoatiia542.GraduationProject.service.workout;
import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.workouts.ExerciseDto;
import com.abdoatiia542.GraduationProject.mapper.ExerciseMapper;
import com.abdoatiia542.GraduationProject.repository.workouts.ExerciseRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.WorkoutSessionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImp implements WorkoutService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;
    private final WorkoutSessionsRepository workoutSessionsRepository;


    public ApiResponse getExercisesByBodyFocus(String bodyFocusName) {
        List<ExerciseDto> exercises = exerciseRepository
                .findByBodyFocuses(bodyFocusName)
                .stream()
                .map(exerciseMapper::toDto)
                .toList();

        if (exercises.isEmpty()) {
            return ApiResponse.failure("No exercises found for body focus: " + bodyFocusName);
        }

        return ApiResponse.success("Exercises fetched successfully", exercises);
    }
    public ApiResponse getExercisesByTrainingLevel(String level) {
        List<ExerciseDto> exercises = workoutSessionsRepository.findExercisesByTrainingLevel(level).stream()
                .map(exerciseMapper::toDto)
                .distinct() //
                .toList();

        if (exercises.isEmpty()) {
            return ApiResponse.failure("No exercises found for training level: " + level);
        }

        return ApiResponse.success("Exercises fetched successfully", exercises);
    }

}
