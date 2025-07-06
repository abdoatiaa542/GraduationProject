package com.abdoatiia542.GraduationProject.repository.food;

import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.food.CompletedMeal;
import com.abdoatiia542.GraduationProject.model.food.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CompletedMealRepository extends JpaRepository<CompletedMeal, Long> {
    boolean existsByTraineeAndMealAndDate(Trainee trainee, Meal meal, LocalDate date);
}
