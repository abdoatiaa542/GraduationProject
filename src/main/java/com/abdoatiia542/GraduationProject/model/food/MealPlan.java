package com.abdoatiia542.GraduationProject.model.food;

import com.abdoatiia542.GraduationProject.model.Trainee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "meal_plans")
public class MealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double calories;
    private double protein;
    private double fat;
    private double carbs;

    private boolean vegetarian;

    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "mealPlans")
    private List<Trainee> trainees;

    @OneToMany(mappedBy = "mealPlan", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, orphanRemoval = true)
    private List<Meal> meals;
}
