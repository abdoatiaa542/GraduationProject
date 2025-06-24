package com.abdoatiia542.GraduationProject.service.workout;

import com.abdoatiia542.GraduationProject.dto.workouts.ExerciseDto;
import com.abdoatiia542.GraduationProject.mapper.WorkoutPlanMapper;
import com.abdoatiia542.GraduationProject.model.workout.Exercise;
import com.abdoatiia542.GraduationProject.model.workout.TrainingLevel;
import com.abdoatiia542.GraduationProject.repository.workouts.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public List<ExerciseDto> getRandom8ByTrainingLevel(TrainingLevel level) {
        List<Exercise> exercises = exerciseRepository.findAllByTrainingLevel(level);

        if (exercises.isEmpty()) {
            return Collections.emptyList(); // سيبه فاضي وخليه يتعامل في الكنترولر
        }

        Collections.shuffle(exercises);

        return exercises.stream()
                .limit(8)
                .map(WorkoutPlanMapper::toDto)
                .toList();
    }

}
