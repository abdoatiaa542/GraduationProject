package com.abdoatiia542.GraduationProject.mapper;

import com.abdoatiia542.GraduationProject.dto.TraineeRegistrationRequest;
import com.abdoatiia542.GraduationProject.dto.TraineeRegistrationResponse;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.enumerations.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class TraineeRegistrationRequestMapper implements Function<TraineeRegistrationRequest, Trainee> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Trainee apply(TraineeRegistrationRequest request) {
        Trainee trainee = new Trainee();
        trainee.setUsername(request.username());
        trainee.setEmail(request.email());
        trainee.setPassword(passwordEncoder.encode(request.password()));
        trainee.setRole(Role.TRAINEE);
        return trainee;
    }

    public TraineeRegistrationResponse toResponse(Trainee trainee) {
        return new TraineeRegistrationResponse(
                trainee.getId(),
                trainee.getUsername(),
                trainee.getEmail()
        );
    }
}
