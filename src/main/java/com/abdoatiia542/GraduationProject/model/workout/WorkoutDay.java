package com.abdoatiia542.GraduationProject.model.workout;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "workout_days")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_plan_id", nullable = false)
    private WorkoutPlan workoutPlan;

    @Column(name = "day_number", nullable = false)
    @Min(1)
    @Max(7)
    private Integer dayNumber;

    @Column(name = "day_name", nullable = false, length = 20)
    private String dayName;

    @Column(name = "is_rest_day")
    private Boolean isRestDay = false;

    @Column(name = "rest_day_notes", columnDefinition = "TEXT")
    private String restDayNotes;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "workoutDay", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("exerciseOrder ASC")
    private List<Exercise> exercises = new ArrayList<>();
}