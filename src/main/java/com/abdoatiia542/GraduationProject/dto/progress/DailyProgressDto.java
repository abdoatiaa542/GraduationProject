package com.abdoatiia542.GraduationProject.dto.progress;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyProgressDto {
    private LocalDate date;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer totalExercisesCount;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer totalBurnedCalories;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer totalTrainingSeconds;

}
