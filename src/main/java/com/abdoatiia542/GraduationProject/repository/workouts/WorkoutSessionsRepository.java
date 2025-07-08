package com.abdoatiia542.GraduationProject.repository.workouts;

import com.abdoatiia542.GraduationProject.model.enumerations.TrainingLevel;
import com.abdoatiia542.GraduationProject.model.exercises.WorkoutSessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutSessionsRepository extends JpaRepository<WorkoutSessions, Long> {
    ;

    Optional<WorkoutSessions> findById(Integer id);

    List<WorkoutSessions> findByTrainingLevel(TrainingLevel level);
}
