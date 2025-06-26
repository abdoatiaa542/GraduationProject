package com.abdoatiia542.GraduationProject.service.workout;

import com.abdoatiia542.GraduationProject.dto.workouts.ExerciseDto;
import com.abdoatiia542.GraduationProject.mapper.WorkoutPlanMapper;
import com.abdoatiia542.GraduationProject.model.enumerations.BodyFocus;
import com.abdoatiia542.GraduationProject.model.workout.Exercise;
import com.abdoatiia542.GraduationProject.model.workout.TrainingLevel;
import com.abdoatiia542.GraduationProject.repository.workouts.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    Pageable pageable = PageRequest.of(0, 8);
        public List<ExerciseDto> getExercisesByTrainingLevel(TrainingLevel level) {
            List<Exercise> exercises = exerciseRepository.findAllExercisesByTrainingLevel(level);

            if (exercises.isEmpty()) {
                return Collections.emptyList(); // هنتعامل معاها في الكنترولر
            }

            Collections.shuffle(exercises);

            return exercises.stream()
                    .limit(8)
                    .map(WorkoutPlanMapper::toDto)
                    .toList();
        }

        public List<ExerciseDto> getExercisesByBodyFocus(BodyFocus level) {
            Pageable pageable = PageRequest.of(0, 8);
            return exerciseRepository.findByBodyFocus(level, pageable)
                    .stream()
                    .map(WorkoutPlanMapper::toDto)
                    .toList();
        }

}

