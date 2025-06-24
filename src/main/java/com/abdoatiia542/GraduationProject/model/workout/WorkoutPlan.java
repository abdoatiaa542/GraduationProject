package com.abdoatiia542.GraduationProject.model.workout;

import com.abdoatiia542.GraduationProject.model.Trainee;
import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workout_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "workoutDays") // 👈 تمنع الطباعة الدائرية
public class WorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bmi_level", nullable = false)
    private Integer bmiLevel;

    @Column(name = "training_split", nullable = false, length = 100)
    private String trainingSplit;

    @Column(name = "goal", nullable = false, columnDefinition = "TEXT")
    private String goal;

    @Enumerated(EnumType.STRING)
    @Column(name = "training_level", nullable = false)
    private TrainingLevel trainingLevel;

    @Column(name = "color_gradient", length = 100)
    private String colorGradient = "from-blue-500 to-purple-600";   // will be remover later

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "workoutPlan", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    private List<Trainee> trainees = new ArrayList<>();


    @Builder.Default
    @OneToMany(mappedBy = "workoutPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("dayNumber ASC")
    private List<WorkoutDay> workoutDays = new ArrayList<>();

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlanConsideration> considerations = new ArrayList<>();
}