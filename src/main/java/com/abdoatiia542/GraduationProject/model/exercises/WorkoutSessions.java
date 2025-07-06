package com.abdoatiia542.GraduationProject.model.exercises;


import com.abdoatiia542.GraduationProject.model.enumerations.TrainingLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "workout_sessions")
public class WorkoutSessions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "imageUrl", nullable = true)
    private String image;


    @Enumerated(EnumType.STRING)
    @Column(name = "training_level")
    private TrainingLevel trainingLevel;


    @Column(columnDefinition = "TEXT")
    private String description;


    @ManyToMany()
    @JoinTable(name = "workout_sessions_exercises",
            joinColumns = @JoinColumn(name = "workout_sessions_id"),
            inverseJoinColumns = @JoinColumn(name = "exercises_id"))
    private List<Exercise> exercises = new ArrayList<>();


}
