package com.abdoatiia542.GraduationProject.repository.workouts;

import com.abdoatiia542.GraduationProject.model.plan.TraineePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TraineePlanRepository extends JpaRepository<TraineePlan, Long> {

    Optional<TraineePlan> findById(Long aLong);
}
