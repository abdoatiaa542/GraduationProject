package com.abdoatiia542.GraduationProject.model.food;

import com.abdoatiia542.GraduationProject.model.Trainee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @Column(nullable = false, name = "total_calories")
    private double totalCalories;
    @Column(nullable = false, name = "total_carbs")
    private double totalCarbs;
    @Column(nullable = false, name = "total_protein")
    private double totalProtein;
    @Column(nullable = false, name = "total_fat")
    private double totalFat;

    @Column(nullable = false, name = "date")
    private LocalDate date;

    @OneToMany(mappedBy = "mealPlan", cascade = {
            CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Meal> meals;

    @ManyToOne
    private Trainee trainee;




}
