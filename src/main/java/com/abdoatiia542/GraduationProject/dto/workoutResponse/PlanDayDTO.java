package com.abdoatiia542.GraduationProject.dto.workoutResponse;
import lombok.Data;
import java.util.List;

@Data
public class PlanDayDTO {
    private Integer id;
    private Integer dayNumber;
    private String note;
    private List<WorkoutSessionDTO> workoutSessions;
}
