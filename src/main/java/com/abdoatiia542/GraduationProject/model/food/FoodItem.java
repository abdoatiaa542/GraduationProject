package com.abdoatiia542.GraduationProject.model.food;

import com.abdoatiia542.GraduationProject.model.enumerations.MealType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "food_items")
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double calories;
    private double protein;
    private double fat;
    private double carbs;
    private boolean vegetarian;

    @Enumerated(EnumType.STRING)
    private MealType type; // BREAKFAST, LUNCH, DINNER, SNACK
}
