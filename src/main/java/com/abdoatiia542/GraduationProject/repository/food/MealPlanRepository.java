package com.abdoatiia542.GraduationProject.repository.food;

import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.food.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {

    // آخر MealPlan لهذا المتدرب
    Optional<MealPlan> findTopByTraineeOrderByIdDesc(Trainee trainee);

    // MealPlan بتاريخ معين
    Optional<MealPlan> findByTraineeAndDate(Trainee trainee, LocalDate date);
}
