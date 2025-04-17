package com.abdoatiia542.GraduationProject.service.auth;

import com.abdoatiia542.GraduationProject.dto.*;
import com.abdoatiia542.GraduationProject.handler.ResourceAlreadyExistsException;
import com.abdoatiia542.GraduationProject.handler.ResourceNotFoundException;
import com.abdoatiia542.GraduationProject.mapper.TraineeRegistrationRequestMapper;
import com.abdoatiia542.GraduationProject.model.AccessToken;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.User;
import com.abdoatiia542.GraduationProject.repository.TraineeRepository;
import com.abdoatiia542.GraduationProject.repository.UserRepository;
import com.abdoatiia542.GraduationProject.service.access_token.AccessTokenService;
import com.abdoatiia542.GraduationProject.service.jwt.BearerTokenWrapper;
import com.abdoatiia542.GraduationProject.utils.ContextHolderUtils;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final BearerTokenWrapper bearerTokenWrapper;
    private final AccessTokenService accessTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TraineeRepository traineeRepository;
    private final TraineeRegistrationRequestMapper traineeRegistrationRequestMapper;

    @Override
    public Object registerTrainee(TraineeRegistrationRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new ResourceAlreadyExistsException("User with this email already exists");
        }
        Trainee trainee = traineeRegistrationRequestMapper.apply(request);
        traineeRepository.save(trainee);
        TraineeRegistrationResponse response = traineeRegistrationRequestMapper.toResponse(trainee);
        return ApiResponse.of("Trainee registered successfully.", response);
    }

    @Override
    public Object completeTraineeRegistration(TraineeRegistrationCompleteRequest request) {
        Trainee trainee = (Trainee) ContextHolderUtils.getUser();
        trainee.setFirstName(request.firstName());
        trainee.setLastName(request.lastName());
        trainee.setGender(request.gender());
        trainee.setBirthDate(request.birthDate());

        traineeRepository.save(trainee);
        return ApiResponse.of("Trainee profile completed successfully.");
    }

    @Override
    public Object loginUser(Object object) {
        LoginRequest request = (LoginRequest) object;
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        if (Objects.nonNull(authentication) && authentication.isAuthenticated() && authentication.getPrincipal() instanceof User user) {

            AccessToken accessToken = accessTokenService.create(user);

            if (Strings.hasText(request.deviceToken())) {
                user.getDeviceTokens().add(request.deviceToken().strip());
                userRepository.save(user);
            }

            String response = accessToken.getToken();
            String message = "Successful user login.";

            return new LoginResponse(message,response,user.getEmail(),user.getUsername(),user.getGender().name(),user.getRole().name());
        }

        throw new UsernameNotFoundException("Invalid username or password");
    }

    @Override
    public Object logoutUser(String deviceToken) {
        System.out.println("i am here .............");
        String bearerToken = bearerTokenWrapper.getToken();
        if (!Strings.hasText(bearerToken)) {
            throw new IllegalArgumentException("Bearer token must not be null or empty");
        }
        accessTokenService.delete(bearerToken);

        if (Strings.hasText(deviceToken)) {
            User currentUser = ContextHolderUtils.getUser();
            currentUser.getDeviceTokens().remove(deviceToken.strip());
            userRepository.save(currentUser);
        }

        return ApiResponse.of("User logged out successfully.");
    }

    @Override
    public Object existsByEmail(String email) {
        return ApiResponse.of("Existence state: ", userRepository.existsByEmailIgnoreCase(email));
    }

    @Override
    public Object existsByUsername(String username) {
        return ApiResponse.of("Existence state: ", userRepository.existsByUsernameIgnoreCase(username));
    }

}

