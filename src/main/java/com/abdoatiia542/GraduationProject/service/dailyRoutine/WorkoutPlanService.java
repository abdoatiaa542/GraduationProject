package com.abdoatiia542.GraduationProject.service.dailyRoutine;


import com.abdoatiia542.GraduationProject.dto.ai.AIServicePredictRequest;
import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.dailyRoutine.DailyWorkoutResponse;
import com.abdoatiia542.GraduationProject.dto.workoutResponse.PlanDTO;
import com.abdoatiia542.GraduationProject.dto.workoutResponse.WorkoutSessionDTO;
import com.abdoatiia542.GraduationProject.handler.NotFoundException;
import com.abdoatiia542.GraduationProject.mapper.PlanMapper;
import com.abdoatiia542.GraduationProject.mapper.WorkoutSessionMapper;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.exercises.PlanDays;
import com.abdoatiia542.GraduationProject.model.exercises.Plans;
import com.abdoatiia542.GraduationProject.model.exercises.TraineePlan;
import com.abdoatiia542.GraduationProject.repository.TraineeRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.PlansRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.TraineePlanRepository;
import com.abdoatiia542.GraduationProject.utils.context.ContextHolderUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkoutPlanService {

    private final TraineePlanRepository traineePlanRepository;
    private final PlansRepository plansRepository;
    private final TraineeRepository traineeRepo;
    final RestTemplate restTemplate = new RestTemplate();

    private static final String WORKOUT_PLAN_URL = "https://abdowa7eed.pythonanywhere.com/api/workout-plan-predict";


    @Transactional
    public ApiResponse getDailyWorkout() {
        Trainee trainee = ContextHolderUtils.getTrainee();
        LocalDate today = LocalDate.now();

        List<TraineePlan> plans = traineePlanRepository.findAllByTrainee(trainee);

        TraineePlan activePlan = plans.stream()
                .filter(TraineePlan::isActive)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No active plan found for today."));

        Plans plan = activePlan.getPlan();

        int totalDays = plan.getPlanDays().size();
        if (totalDays == 0) {
            return ApiResponse.failure("This plan has no workout days.");
        }

        long daysSinceStart = ChronoUnit.DAYS.between(activePlan.getStartDate(), today);
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


    @Transactional
    public ApiResponse generatePlanForTrainee() {
        Trainee trainee = fetchAuthenticatedTrainee();
        LocalDate today = LocalDate.now();
        AIServicePredictRequest request = AIServicePredictRequest.buildRequestFromTrainee(trainee);
        Integer planId = fetchPlanIdFromAI(request);


        List<TraineePlan> plans = traineePlanRepository.findAllByTrainee(trainee);
        boolean hasActivePlan = plans.stream()
                .anyMatch(TraineePlan::isActive);

        if (hasActivePlan) {
            throw new IllegalArgumentException("Trainee already has an active plan.");
        }
        Plans plan = plansRepository.findById(planId)
                .orElseThrow(() -> new NotFoundException("Plan not found"));

        TraineePlan traineePlan = assignPlanToTrainee(trainee, plan);

        traineePlanRepository.save(traineePlan);

        PlanDTO dto = PlanMapper.mapToDTO(plan);
        return ApiResponse.success("Plan assigned to trainee", dto);
    }


    private Trainee fetchAuthenticatedTrainee() {
        Long traineeId = ContextHolderUtils.getTrainee().getId();
        return traineeRepo.findById(traineeId)
                .orElseThrow(() -> new NotFoundException("Trainee not found"));
    }


    private Integer fetchPlanIdFromAI(AIServicePredictRequest requestData) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    WORKOUT_PLAN_URL,
                    requestData,
                    String.class
            );

            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> result = mapper.readValue(response.getBody(), new TypeReference<>() {});
            return (Integer) result.get(0).get("plan");
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response from AI model: " + e.getMessage(), e);
        }
    }

    private TraineePlan assignPlanToTrainee(Trainee trainee, Plans plan) {
        return TraineePlan.builder()
                .trainee(trainee)
                .plan(plan)
                .startDate(LocalDate.now())
                .build();
    }





}