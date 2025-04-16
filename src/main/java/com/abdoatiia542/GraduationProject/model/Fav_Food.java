package com.abdoatiia542.GraduationProject.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Fav_Food")
public class Fav_Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainee_id", nullable = false, updatable = false)
    private Trainee trainee;

    private String Food_Name;

    private Integer Calories;

    private BigDecimal Protein;

    private BigDecimal Carbs;

    private BigDecimal Fat;

    LocalDate creLocalDate;

    LocalDate updLocalDate;


}
