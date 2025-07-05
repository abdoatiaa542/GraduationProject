package com.abdoatiia542.GraduationProject.repository.workouts;

import com.abdoatiia542.GraduationProject.model.enumerations.TrainingLevel;
import com.abdoatiia542.GraduationProject.model.plan.Exercise;
import com.abdoatiia542.GraduationProject.model.plan.WorkoutSessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutSessionsRepository extends JpaRepository<WorkoutSessions, Long> {


        List<WorkoutSessions> findByTrainingLevel(TrainingLevel level);

        Optional<WorkoutSessions> findById(Integer id);


}
