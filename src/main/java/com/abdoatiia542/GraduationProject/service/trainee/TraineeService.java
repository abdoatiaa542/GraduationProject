package com.abdoatiia542.GraduationProject.service.trainee;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.workoutResponse.PlanDTO;
import com.abdoatiia542.GraduationProject.handler.NotFoundException;
import com.abdoatiia542.GraduationProject.mapper.PlanMapper;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.exercises.Plans;
import com.abdoatiia542.GraduationProject.model.exercises.TraineePlan;
import com.abdoatiia542.GraduationProject.repository.TraineeRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.PlansRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.TraineePlanRepository;
import com.abdoatiia542.GraduationProject.utils.SecurityUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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


}
