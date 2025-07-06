package com.abdoatiia542.GraduationProject.service.dailyRoutine;


import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.dailyRoutine.DailyWorkoutResponse;
import com.abdoatiia542.GraduationProject.dto.workoutResponse.WorkoutSessionDTO;
import com.abdoatiia542.GraduationProject.mapper.WorkoutSessionMapper;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.exercises.PlanDays;
import com.abdoatiia542.GraduationProject.model.exercises.Plans;
import com.abdoatiia542.GraduationProject.model.exercises.TraineePlan;
import com.abdoatiia542.GraduationProject.repository.workouts.TraineePlanRepository;
import com.abdoatiia542.GraduationProject.utils.context.ContextHolderUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyRoutineService {

    private final TraineePlanRepository traineePlanRepository;

    @Transactional
    public ApiResponse getDailyWorkout() {
        Trainee trainee = ContextHolderUtils.getTrainee();
        LocalDate today = LocalDate.now();

        Optional<TraineePlan> optionalPlan = traineePlanRepository
                .findTraineePlanByStartDateAndTrainee(today, trainee);

        if (optionalPlan.isEmpty()) {
            return ApiResponse.failure("No active plan found for today.");
        }

        TraineePlan traineePlan = optionalPlan.get();
        Plans plan = traineePlan.getPlan();

        int totalDays = plan.getPlanDays().size();
        if (totalDays == 0) {
            return ApiResponse.failure("This plan has no workout days.");
        }

        long daysSinceStart = ChronoUnit.DAYS.between(traineePlan.getStartDate(), today);
        int currentDayNumber = (int) (daysSinceStart % totalDays) + 1;

        Optional<PlanDays> currentPlanDay = plan.getPlanDays().stream()
                .filter(d -> d.getDayNumber().equals(currentDayNumber))
                .findFirst();

        if (currentPlanDay.isEmpty()) {
            return ApiResponse.failure("No workouts found for today's plan day.");
        }

        List<WorkoutSessionDTO> workoutDtos = WorkoutSessionMapper
                .mapToDtoList(currentPlanDay.get().getWorkoutSessions());

        DailyWorkoutResponse response = DailyWorkoutResponse.builder()
                .traineeId(trainee.getId())
                .date(today)
                .dayNumber(currentDayNumber)
                .planName(plan.getName())
                .planGoal(plan.getGoal())
                .trainingLevel(plan.getTrainingLevel())
                .note(currentPlanDay.get().getNote())
                .workoutSessions(workoutDtos)
                .build();

        return ApiResponse.success("Daily workout fetched successfully", response);
    }


//    public ApiResponse getDailyMeals() {
//
//    }
}