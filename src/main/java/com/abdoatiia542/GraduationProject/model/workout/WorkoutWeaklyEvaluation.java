package com.abdoatiia542.GraduationProject.model.workout;

import jakarta.persistence.*;

@Entity
public class WorkoutWeaklyEvaluation {   // 12 weeks :

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserWorkoutProgress progress;

    private int weekNumber;
    private double score;
    private String notes;

}
 // plan >>  12 weeks >>  7days