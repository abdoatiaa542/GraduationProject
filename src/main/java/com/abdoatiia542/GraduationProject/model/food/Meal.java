package com.abdoatiia542.GraduationProject.model.food;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "meals")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;


    @Column(nullable = false, name = "calories")
    private double calories;
    @Column(nullable = false, name = "total_carbs")
    private double carbs;
    @Column(nullable = false, name = "protein")
    private double protein;
    @Column(nullable = false, name = "fat")
    private double fat;


    @ManyToOne
    @JoinColumn(name = "meal_plan_id")
    private MealPlan mealPlan;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealItems> items = new ArrayList<>();
}
