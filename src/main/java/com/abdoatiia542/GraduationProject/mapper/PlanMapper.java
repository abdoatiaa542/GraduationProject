package com.abdoatiia542.GraduationProject.mapper;

import com.abdoatiia542.GraduationProject.dto.dailyRoutine.ExerciseDto;
import com.abdoatiia542.GraduationProject.dto.workoutResponse.PlanDTO;
import com.abdoatiia542.GraduationProject.dto.workoutResponse.PlanDayDTO;
import com.abdoatiia542.GraduationProject.dto.workoutResponse.WorkoutSessionDTO;
import com.abdoatiia542.GraduationProject.model.exercises.Plans;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PlanMapper {

    public PlanDTO mapToDTO(Plans plan) {
        PlanDTO dto = new PlanDTO();
        dto.setId(plan.getId());
        dto.setName(plan.getName());
        dto.setGoal(plan.getGoal());
        dto.setTrainingLevel(plan.getTrainingLevel().name());
        dto.setTrainingSplit(plan.getTrainingSplit());

        List<PlanDayDTO> dayDTOs = plan.getPlanDays().stream().map(day -> {
            PlanDayDTO dayDTO = new PlanDayDTO();
            dayDTO.setId(day.getId());
            dayDTO.setDayNumber(day.getDayNumber());
            dayDTO.setNote(day.getNote());

            List<WorkoutSessionDTO> sessionDTOs = day.getWorkoutSessions().stream().map(session -> {
                WorkoutSessionDTO sessionDTO = new WorkoutSessionDTO();
                sessionDTO.setId(session.getId());
                sessionDTO.setName(session.getName());
                sessionDTO.setImage(session.getImage());
                sessionDTO.setDescription(session.getDescription());
                sessionDTO.setImage(session.getImage());
                sessionDTO.setTrainingLevel(session.getTrainingLevel().name());

                List<ExerciseDto> exerciseDTOs = session.getExercises().stream().map(ex -> {
                    ExerciseDto exDTO = new ExerciseDto();
                    exDTO.setId(ex.getId());
                    exDTO.setName(ex.getName());
                    exDTO.setDescription(ex.getDescription());
                    exDTO.setImageLink(ex.getImageLink());
                    exDTO.setVideoLink(ex.getVideoLink());
                    exDTO.setReps(ex.getReps());
                    exDTO.setSets(ex.getSets());
                    exDTO.setDurationSeconds(ex.getDurationSeconds());
                    exDTO.setDurationRestSeconds(ex.getDurationRestSeconds());
                    exDTO.setCaloriesBurned(ex.getCaloriesBurned());
                    exDTO.setTotalCalories(ex.getTotalCalories());
                    return exDTO;
                }).toList();

                sessionDTO.setExercises(exerciseDTOs);
                return sessionDTO;
            }).toList();

            dayDTO.setWorkoutSessions(sessionDTOs);
            return dayDTO;
        }).toList();

        dto.setPlanDays(dayDTOs);
        return dto;
    }

}
