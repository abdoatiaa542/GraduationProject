package com.abdoatiia542.GraduationProject.service.trainee;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.workoutResponse.PlanDTO;
import com.abdoatiia542.GraduationProject.handler.NotFoundException;
import com.abdoatiia542.GraduationProject.mapper.PlanMapper;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.plan.Plans;
import com.abdoatiia542.GraduationProject.model.plan.TraineePlan;
import com.abdoatiia542.GraduationProject.repository.TraineeRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.PlansRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.TraineePlanRepository;
import com.abdoatiia542.GraduationProject.utils.SecurityUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TraineeService {

    private final TraineeRepository traineeRepo;
    private final PlansRepository plansRepository;
    private final TraineePlanRepository traineePlanRepository;
    private final PlanMapper planMapper;
    public Trainee getCurrentTrainee() {
        String username = SecurityUtils.getCurrentUserEmail(); // راجع الاسم ده كويس
        return traineeRepo.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new NotFoundException("Trainee not found for username: " + username));
    }
    @Transactional
    public ApiResponse generatePlanForTrainee() {

        Long traineeId=getCurrentTrainee().getId();


        Trainee trainee = traineeRepo.findById(traineeId)
                .orElseThrow(() -> new NotFoundException("Trainee not found"));

        Map<String, Object> requestData = Map.of(
                "Weight", trainee.getWeight(),
                "Height", trainee.getHeight(),
                "Age", (LocalDate.now().getYear() - trainee.getBirthYear()),
                "Gender", capitalize(trainee.getGender().name().toLowerCase()),
                "Goal", formatGoal(trainee.getGoal().toString()),
                "ActivityLevel", capitalize(trainee.getActivityLevel().toString().toLowerCase())
        );

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://abdowa7eed.pythonanywhere.com/api/workout-plan-predict",
                requestData,
                String.class
        );

        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> responseList = mapper.readValue(response.getBody(), new TypeReference<>() {});
            Integer planId = (Integer) responseList.get(0).get("plan");

            Plans plan = plansRepository.findById(planId)
                    .orElseThrow(() -> new NotFoundException("Plan not found"));

            TraineePlan traineePlan = TraineePlan.builder()
                    .trainee(trainee)
                    .plan(plan)
                    .startDate(LocalDate.now())
                    .build();

            traineePlanRepository.save(traineePlan);
            PlanDTO dto = planMapper.mapToDTO(plan);
            return ApiResponse.success("Plan assigned to trainee", dto);


        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response from AI model: " + e.getMessage(), e);
        }
    }
    private String formatGoal(String goal) {
        return switch (goal.toUpperCase()) {
            case "FAT_LOSS" -> "Fat Loss";
            case "MUSCLE_GAIN" -> "Muscle Gain";
            case "STAY_FIT" -> "Stay Fit";
            default -> goal;
        };
    }
    private String capitalize(String value) {
        if (value == null || value.isEmpty()) return value;
        return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
    }

}
