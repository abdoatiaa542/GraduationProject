package com.abdoatiia542.GraduationProject.repository.workouts;

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
        @Query("""
    SELECT DISTINCT e
    FROM WorkoutSessions ws
    JOIN ws.exercises e
    JOIN FETCH e.bodyFocuses
    WHERE LOWER(ws.trainingLevel) = LOWER(:level)
""")
        List<Exercise> findExercisesByTrainingLevel(@Param("level") String level);

        Optional<WorkoutSessions> findById(Integer id);


}
