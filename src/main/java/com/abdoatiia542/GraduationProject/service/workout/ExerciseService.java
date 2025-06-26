package com.abdoatiia542.GraduationProject.service.workout;

import com.abdoatiia542.GraduationProject.dto.workouts.ExerciseDto;
import com.abdoatiia542.GraduationProject.mapper.WorkoutPlanMapper;
import com.abdoatiia542.GraduationProject.model.workout.Exercise;
import com.abdoatiia542.GraduationProject.model.workout.TrainingLevel;
import com.abdoatiia542.GraduationProject.repository.workouts.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
        public List<ExerciseDto> getRandomExercisesByLevel(TrainingLevel level) {
            List<Exercise> exercises = exerciseRepository.findAllByTrainingLevel(level);

            if (exercises.isEmpty()) {
                return Collections.emptyList(); // هنتعامل معاها في الكنترولر
            }

            Collections.shuffle(exercises);

            return exercises.stream()
                    .limit(8)
                    .map(WorkoutPlanMapper::toDto)
                    .toList();
        }
    }

