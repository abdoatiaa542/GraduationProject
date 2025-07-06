package com.abdoatiia542.GraduationProject.model;

import com.abdoatiia542.GraduationProject.model.embeddables.BodyFatRange;
import com.abdoatiia542.GraduationProject.model.enumerations.ActivityLevel;
import com.abdoatiia542.GraduationProject.model.enumerations.Goal;
import com.abdoatiia542.GraduationProject.model.enumerations.TrainingLevel;
import com.abdoatiia542.GraduationProject.model.exercises.WorkoutSessions;
import com.abdoatiia542.GraduationProject.model.food.MealPlan;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trainees")
public class Trainee extends User {

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private Double height;

    @Column
    private Double weight;

    @Column
    private Double targetWeight;

    @Enumerated(EnumType.STRING)
    private Goal goal;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "min", column = @Column(name = "body_fat_min")),
            @AttributeOverride(name = "max", column = @Column(name = "body_fat_max"))
    })
    @Valid
    private BodyFatRange bodyFat;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "min", column = @Column(name = "target_body_fat_min")),
            @AttributeOverride(name = "max", column = @Column(name = "target_body_fat_max"))
    })
    @Valid
    private BodyFatRange targetBodyFat;

    @Enumerated(EnumType.STRING)
    private TrainingLevel trainingLevel;

    @Enumerated(EnumType.STRING)
    private ActivityLevel activityLevel;

    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealPlan> mealPlans;

    @ManyToMany
    @JoinTable(name = "user_saved_workouts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "workout_id"))
    private List<WorkoutSessions> savedWorkouts = new ArrayList<>();


    public boolean isMeasurementsSet() {
        return height != null
                && weight != null
                && targetWeight != null
                && goal != null
                && bodyFat != null
                && targetBodyFat != null
                && trainingLevel != null
                && activityLevel != null;
    }

}
