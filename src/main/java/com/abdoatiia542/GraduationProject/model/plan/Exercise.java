package com.abdoatiia542.GraduationProject.model.plan;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String ImageLink;

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

    @ManyToMany(mappedBy = "exercises")
    private List<WorkoutSessions> sessions;

    @OneToMany
    private List<BodyFocus> bodyFocuses;

}
