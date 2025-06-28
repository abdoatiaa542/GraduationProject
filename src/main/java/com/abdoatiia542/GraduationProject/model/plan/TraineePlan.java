package com.abdoatiia542.GraduationProject.model.plan;


import com.abdoatiia542.GraduationProject.model.Trainee;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "trainee_plans")
public class TraineePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainee_id", nullable = false)
    private Trainee trainee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plans plan;

}
