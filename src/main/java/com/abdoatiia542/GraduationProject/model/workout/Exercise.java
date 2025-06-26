package com.abdoatiia542.GraduationProject.model.workout;

import com.abdoatiia542.GraduationProject.model.enumerations.BodyFocus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_day_id", nullable = false)
    private WorkoutDay workoutDay;

    @Column(name = "exercise_name", nullable = false, length = 200)
    private String exerciseName;

    @Column(name = "sets_count", nullable = false)
    private Integer setsCount;

    @Column(name = "reps", nullable = false, length = 50)
    private String reps;

    @Column(name = "rest_seconds", nullable = false)
    private Integer restSeconds = 60;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "exercise_order")
    private Integer exerciseOrder = 1;

    @Enumerated(EnumType.STRING)
    @Column(name = "body_focus", nullable = false)
    private BodyFocus bodyFocus;

    @Column(name = "calories_burned")
    private Integer caloriesBurned;

    @Column(name = "total_calories")
    private Integer totalCalories;

    @Column(name = "is_completed")
    private Boolean isCompleted = false;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "video_url", length = 500)
    private String videoUrl;

}
