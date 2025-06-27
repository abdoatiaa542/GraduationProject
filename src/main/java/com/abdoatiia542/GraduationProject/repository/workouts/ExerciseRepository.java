package com.abdoatiia542.GraduationProject.repository.workouts;

import com.abdoatiia542.GraduationProject.model.enumerations.BodyFocus;
import com.abdoatiia542.GraduationProject.model.workout.Exercise;
import com.abdoatiia542.GraduationProject.model.workout.TrainingLevel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Query("""
        SELECT e FROM Exercise e
        JOIN FETCH e.workoutDay d
        JOIN FETCH d.workoutPlan p
        WHERE p.trainingLevel = :level
    """)
    List<Exercise> findAllExercisesByTrainingLevel(@Param("level") TrainingLevel level);


    List<Exercise> findByBodyFocus(BodyFocus bodyFocus, Pageable pageable);

    Exercise findByExerciseName(String exerciseName);

}