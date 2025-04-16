package com.abdoatiia542.GraduationProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "achievements")
public class Achievements {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Trainee trainee;

    private String achievements_name;

    private LocalDate achievements_date;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;


}
