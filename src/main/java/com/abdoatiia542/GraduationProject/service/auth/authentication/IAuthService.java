package com.abdoatiia542.GraduationProject.service.auth.authentication;

import com.abdoatiia542.GraduationProject.dto.TraineeMeasurementsRequest;
import com.abdoatiia542.GraduationProject.dto.TraineeRegistrationCompleteRequest;
import com.abdoatiia542.GraduationProject.dto.TraineeRegistrationRequest;
import org.springframework.lang.Nullable;

public interface IAuthService {


    Object registerTrainee(TraineeRegistrationRequest request);

    Object completeTraineeRegistration(TraineeRegistrationCompleteRequest request);

    Object CompleteTraineeMeasurements(TraineeMeasurementsRequest request);

    Object loginUser(Object object);

    Object logoutUser(@Nullable String deviceToken);

    Object existsByEmail(String email);

    Object existsByUsername(String username);

    Object refreshToken(String token);



}
