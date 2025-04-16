package com.abdoatiia542.GraduationProject.model;


import com.google.shopping.type.Weight;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trainee_measurements")
public class Trainee_Measurements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    Trainee trainee;

    BigDecimal weight;

    BigDecimal height;

    BigDecimal Body_Fat_Percentage;

    BigDecimal Muscle_Mass;

    LocalDate creLocalDate;

    LocalDate updLocalDate;


}
