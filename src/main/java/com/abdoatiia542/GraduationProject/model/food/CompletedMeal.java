package com.abdoatiia542.GraduationProject.model.food;

import com.abdoatiia542.GraduationProject.model.Trainee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "completed_meals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompletedMeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Trainee trainee;

    @ManyToOne
    private Meal meal;

    private LocalDate date;
}
