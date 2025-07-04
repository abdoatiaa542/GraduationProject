package com.abdoatiia542.GraduationProject.model;

import com.abdoatiia542.GraduationProject.model.embeddables.BodyFatRange;
import com.abdoatiia542.GraduationProject.model.enumerations.ActivityLevel;
import com.abdoatiia542.GraduationProject.model.enumerations.Goal;
import com.abdoatiia542.GraduationProject.model.enumerations.TrainingLevel;
import com.abdoatiia542.GraduationProject.model.food.MealPlan;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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


}
