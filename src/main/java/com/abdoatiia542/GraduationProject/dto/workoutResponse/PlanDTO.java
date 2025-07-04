package com.abdoatiia542.GraduationProject.dto.workoutResponse;
import lombok.Data;
import java.util.List;

@Data
public class PlanDTO {
    private Integer id;
    private String name;
    private String goal;
    private String trainingLevel;
    private String trainingSplit;
    private List<PlanDayDTO> planDays;
}
