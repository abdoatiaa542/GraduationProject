package com.abdoatiia542.GraduationProject.repository;

import com.abdoatiia542.GraduationProject.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TraineeRepository extends JpaRepository<Trainee, Long> {
    boolean existsByEmailIgnoreCase(String email);

    boolean existsByUsernameIgnoreCase(String username);

    Optional<Trainee> findByEmailIgnoreCase(String email);

    Optional<Trainee> findByUsernameIgnoreCase(String username);

    @Query("SELECT t FROM Trainee t LEFT JOIN FETCH t.savedWorkouts WHERE t.id = :id")
    Trainee findByIdWithSavedWorkouts(@Param("id") Long id);


}
