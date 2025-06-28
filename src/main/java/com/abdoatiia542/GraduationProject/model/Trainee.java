package com.abdoatiia542.GraduationProject.model;


import com.abdoatiia542.GraduationProject.model.plan.TraineePlan;
import jakarta.persistence.*;
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

    @Column()
    private String firstName;

    @Column()
    private String lastName;

    @Column()
    private Double height;

    @Column()
    private Double weight;

    @Column()
    private Double targetWeight;


    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TraineePlan> traineePlans = new ArrayList<>();


}
