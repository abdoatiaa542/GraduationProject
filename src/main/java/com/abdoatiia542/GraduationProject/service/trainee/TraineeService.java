package com.abdoatiia542.GraduationProject.service.trainee;

import com.abdoatiia542.GraduationProject.handler.NotFoundException;
import com.abdoatiia542.GraduationProject.mapper.PlanMapper;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.repository.TraineeRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.PlansRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.TraineePlanRepository;
import com.abdoatiia542.GraduationProject.utils.constants.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
