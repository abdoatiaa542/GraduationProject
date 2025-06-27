package com.abdoatiia542.GraduationProject.model;

import com.abdoatiia542.GraduationProject.model.embeddables.BodyFatRange;
import com.abdoatiia542.GraduationProject.model.enumerations.*;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

    @Column
    int birthYear;

}
