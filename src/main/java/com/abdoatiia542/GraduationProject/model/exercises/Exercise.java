package com.abdoatiia542.GraduationProject.model.exercises;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = true)
    private String imageLink;

    @Column(nullable = true)
    private String videoLink;


    @Column(nullable = true)
    private String reps;

    @Column(nullable = true)
    private Integer sets;

    @Column(nullable = true)
    private Integer durationSeconds;

    @Column(nullable = true)
    private Integer caloriesBurned;

    @Column(nullable = true)
    private Integer totalCalories; // sets * caloriesBurned


    @ManyToMany
    @JoinTable(
            name = "exercises_body_focuses",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "body_focus_id")
    )
    private Set<BodyFocus> bodyFocuses = new HashSet<>();


    @Column(nullable = true)
    private Integer durationRestSeconds;
}
