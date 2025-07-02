package com.abdoatiia542.GraduationProject.repository.workouts;

import com.abdoatiia542.GraduationProject.model.plan.PlanDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanDaysRepository extends JpaRepository<PlanDays, Long> {
}
