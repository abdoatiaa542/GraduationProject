package com.abdoatiia542.GraduationProject.service.auth;

import com.abdoatiia542.GraduationProject.dto.*;
import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.handler.ResourceAlreadyExistsException;
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
    // Create tokens
    AccessToken accessToken = accessTokenService.create(trainee);
    AccessToken refreshToken = accessTokenService.refresh(trainee);
    String token = accessToken.getToken();
    String refreshTokenValue = refreshToken.getToken();
    String message = "Trainee registered successfully.";
    LoginResponse response = new LoginResponse(
            message,
            token,
            refreshTokenValue,
            trainee.getEmail(),
            trainee.getUsername(),
            trainee.getRole().name()
    );

    return ApiResponse.success(message, response);
}

    @Override
    public Object completeTraineeRegistration(TraineeRegistrationCompleteRequest request) {
        Trainee trainee = (Trainee) ContextHolderUtils.getUser();
        trainee.setFirstName(request.firstName());
        trainee.setLastName(request.lastName());
        trainee.setGender(request.gender());
        trainee.setBirthDate(request.birthDate());

        traineeRepository.save(trainee);
        return ApiResponse.success("Trainee registration completed successfully.");
    }

    @Override
    public Object loginUser(Object object) {
        try {
            LoginRequest request = (LoginRequest) object;
            Authentication authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

            if (Objects.nonNull(authentication) && authentication.isAuthenticated() && authentication.getPrincipal() instanceof User user) {

                AccessToken accessToken = accessTokenService.create(user);
                AccessToken refreshToken = accessTokenService.refresh(user);

                if (Strings.hasText(request.deviceToken())) {
                    user.getDeviceTokens().add(request.deviceToken().strip());
                    userRepository.save(user);
                }

                String token = accessToken.getToken();
                String refreshTokenValue = refreshToken.getToken();
                String message = "Successful user login.";

                LoginResponse response = new LoginResponse(message, token,refreshTokenValue ,  user.getEmail(), user.getUsername(), user.getRole().name());

                return ApiResponse.success(message, response);
            } else {
                return ApiResponse.failure("Invalid username or password");
            }
        } catch (Exception e) {
            return  ApiResponse.failure("Error logging in user: " + e.getMessage());
        }
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

        return ApiResponse.success("User logged out successfully.");
    }

    @Override
    public Object existsByEmail(String email) {
        return userRepository.existsByEmailIgnoreCase(email)? ApiResponse.success(email + " is exists") : ApiResponse.failure(email + " is not exists");
    }

    @Override
    public Object existsByUsername(String username) {
        return userRepository.existsByUsernameIgnoreCase(username)? ApiResponse.success(username + " is exists") : ApiResponse.failure(username + " is not exists");
    }

}

