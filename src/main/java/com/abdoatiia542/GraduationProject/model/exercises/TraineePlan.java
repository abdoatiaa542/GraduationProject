package com.abdoatiia542.GraduationProject.model.exercises;


import com.abdoatiia542.GraduationProject.model.Trainee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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


    public boolean isActive() {
        if (startDate == null) return false;
        long daysSinceStart = ChronoUnit.DAYS.between(startDate, LocalDate.now());
        return daysSinceStart <= 30;
    }

}
