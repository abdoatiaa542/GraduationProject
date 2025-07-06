package com.abdoatiia542.GraduationProject.repository.workouts;

import com.abdoatiia542.GraduationProject.model.exercises.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Query("SELECT DISTINCT e FROM Exercise e JOIN FETCH e.bodyFocuses bf WHERE LOWER(bf.name) = LOWER(:name)")
     List<Exercise> findByBodyFocuses(@Param("name")String name);
    Optional<Exercise> findById(Integer id);

}