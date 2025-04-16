package com.abdoatiia542.GraduationProject.repository;

import com.abdoatiia542.GraduationProject.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TraineeRepository extends JpaRepository<Trainee, Long> {
    boolean existsByEmailIgnoreCase(String email);

    boolean existsByUsernameIgnoreCase(String username);

    Optional<Trainee> findByEmailIgnoreCase(String email);


}
