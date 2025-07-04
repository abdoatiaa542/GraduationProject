package com.abdoatiia542.GraduationProject.repository.workouts;

import com.abdoatiia542.GraduationProject.model.plan.Plans;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlansRepository extends JpaRepository<Plans, Integer> {

    @EntityGraph(attributePaths = {
            "planDays",
            "planDays.workoutSessions",
            "planDays.workoutSessions.exercises",
            "planDays.workoutSessions.exercises.bodyFocuses"
    })

    Optional<Plans> findById(Integer id);

}
