package com.abdoatiia542.GraduationProject.service.workout;


import com.abdoatiia542.GraduationProject.mapper.WorkoutPlanMapper;
import com.abdoatiia542.GraduationProject.model.workout.WorkoutPlan;
import com.abdoatiia542.GraduationProject.repository.workouts.WorkoutPlanRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;

    public WorkoutPlanService(WorkoutPlanRepository workoutPlanRepository) {
        this.workoutPlanRepository = workoutPlanRepository;
    }

    @Transactional
    public List<Object> getAllWorkoutPlans() {
        log.info("Fetching all workout plans");
        List<WorkoutPlan> plans = workoutPlanRepository.findAll();
        return plans.stream()
                .map(WorkoutPlanMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Object getWorkoutPlanById(Long id) {
        WorkoutPlan plan = workoutPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workout plan not found with id: " + id));
        return WorkoutPlanMapper.toDto(plan);
    }


}