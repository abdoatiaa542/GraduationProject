package com.abdoatiia542.GraduationProject.model.food;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "meal_items")
public class MealItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true, name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;
}
