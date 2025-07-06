package com.abdoatiia542.GraduationProject.dto.dailyRoutine;

import com.abdoatiia542.GraduationProject.model.enumerations.TrainingLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyWorkoutResponse {
    private Long traineeId;
    private LocalDate date;
    private Integer dayNumber;
    private String planName;
    private String planGoal;
    private TrainingLevel trainingLevel;
    private String note;
    private List<WorkoutSessionDto> workoutSessions;
}
