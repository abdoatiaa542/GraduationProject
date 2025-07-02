package com.abdoatiia542.GraduationProject.repository.workouts;

import com.abdoatiia542.GraduationProject.model.plan.BodyFocus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BodyFocusRepository extends JpaRepository<BodyFocus, Long> {

    Optional<BodyFocus> findByNameIgnoreCase(String name);
}
