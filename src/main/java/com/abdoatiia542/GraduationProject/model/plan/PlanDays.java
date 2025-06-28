package com.abdoatiia542.GraduationProject.model.plan;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "plan_days")
public class PlanDays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "day_number")
    private Integer dayNumber;

    @Column(columnDefinition = "TEXT")
    private String note;


    @ManyToOne(optional = false)
    @JoinColumn(name = "plan_id")
    private Plans plan;


    @OneToMany
    @JoinColumn(name = "plan_day_id") // دا هيحط foreign key في workout_sessions
    private List<WorkoutSessions> workoutSessions;


}