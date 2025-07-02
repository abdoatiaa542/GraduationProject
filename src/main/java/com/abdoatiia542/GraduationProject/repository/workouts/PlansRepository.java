package com.abdoatiia542.GraduationProject.repository.workouts;

import com.abdoatiia542.GraduationProject.model.plan.Plans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlansRepository extends JpaRepository<Plans, Long> {
}
