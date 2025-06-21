package com.abdoatiia542.GraduationProject.model.workout;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "plan_considerations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanConsideration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String considerationText;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private WorkoutPlan plan;
}
