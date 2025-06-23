package com.abdoatiia542.GraduationProject.model.workout;

import com.abdoatiia542.GraduationProject.model.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "USER_WORKOUT_PROGRESS")
public class UserWorkoutProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private boolean isCompleted;
    private double totalScore;


    private LocalDate startedAt;
    private LocalDate completedAt;


    private Double startWeight;
    @Column(name = "end_weight", nullable = true)
    private Double endWeight;


    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "progress", cascade = CascadeType.ALL)
    private List<WorkoutWeaklyEvaluation> evaluations = new ArrayList<>();

}
