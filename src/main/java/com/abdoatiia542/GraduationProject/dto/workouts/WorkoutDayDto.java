package com.abdoatiia542.GraduationProject.dto.workouts;

import com.abdoatiia542.GraduationProject.dto.dailyRoutine.ExerciseDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkoutDayDto {
    private Long id;
    private Integer dayNumber;
    private String dayName;
    private Boolean isRestDay;
    private String restDayNotes;
    private LocalDateTime createdAt;
    private List<ExerciseDto> exercises;
}