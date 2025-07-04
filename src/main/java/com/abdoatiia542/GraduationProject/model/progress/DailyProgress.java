package com.abdoatiia542.GraduationProject.model.progress;

import com.abdoatiia542.GraduationProject.model.Trainee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "daily_progress", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"trainee_id", "date"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private Integer totalBurnedCalories;

    private Integer totalExercisesCount;

    private Integer totalTrainingSeconds;
    @ManyToOne
    @JoinColumn(name = "trainee_id", nullable = false)
    private Trainee trainee;
}
