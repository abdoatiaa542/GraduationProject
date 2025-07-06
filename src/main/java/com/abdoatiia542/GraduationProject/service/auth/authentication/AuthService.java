package com.abdoatiia542.GraduationProject.service.auth.authentication;

import com.abdoatiia542.GraduationProject.cloudnairy.CloudinaryService;
import com.abdoatiia542.GraduationProject.dto.*;
import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.handler.ExpiredTokenException;
import com.abdoatiia542.GraduationProject.handler.InvalidTokenException;
import com.abdoatiia542.GraduationProject.handler.ResourceAlreadyExistsException;
import com.abdoatiia542.GraduationProject.mapper.TraineeRegistrationRequestMapper;
import com.abdoatiia542.GraduationProject.model.AccessToken;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.User;
import com.abdoatiia542.GraduationProject.repository.TraineeRepository;
import com.abdoatiia542.GraduationProject.repository.UserRepository;
import com.abdoatiia542.GraduationProject.service.access_token.AccessTokenService;
import com.abdoatiia542.GraduationProject.service.jwt.BearerTokenWrapper;
import com.abdoatiia542.GraduationProject.service.jwt.JwtService;
import com.abdoatiia542.GraduationProject.utils.context.ContextHolderUtils;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
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
    private final JwtService jwtService;
    private final CloudinaryService cloudinaryService;

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
                trainee.getRole().name(),
                trainee.getFirstName(),
                trainee.getLastName(),
                trainee.getGender(),
                trainee.getBirthYear(),
                false
        );

        return ApiResponse.of(message, response);
    }

    @Override
    public Object completeTraineeRegistration(TraineeRegistrationCompleteRequest request) {
        Trainee trainee = (Trainee) ContextHolderUtils.getUser();
        trainee.setFirstName(request.firstName());
        trainee.setLastName(request.lastName());
        trainee.setGender(request.gender());
        trainee.setBirthYear(request.birthYear());

        if (request.image() != null && !request.image().isEmpty()) {
            try {
                Map uploadResult = cloudinaryService.upload(request.image());
                String imageUrl = (String) uploadResult.get("secure_url");
                trainee.setImage(imageUrl);
                traineeRepository.save(trainee);
            } catch (IOException e) {
                return ApiResponse.of("Image upload failed.");
            }
        }


        UserDetailsResponse response = new UserDetailsResponse(
                trainee.getUsername(),
                trainee.getEmail(),
                trainee.getRole().name(),
                trainee.getFirstName(),
                trainee.getLastName(),
                trainee.getGender().name(),
                trainee.getBirthYear(),
                trainee.getImage()
        );

        return ApiResponse.success("Trainee registration completed successfully.", response);
    }


    @Override
    public Object CompleteTraineeMeasurements(TraineeMeasurementsRequest request) {
        Trainee trainee = (Trainee) ContextHolderUtils.getUser();
        trainee.setActivityLevel(request.activityLevel());
        trainee.setHeight(request.height());
        trainee.setWeight(request.weight());
        trainee.setTargetWeight(request.targetWeight());
        trainee.setBodyFat(request.bodyFat());
        trainee.setTargetBodyFat(request.targetBodyFat());
        trainee.setGoal(request.goal());
        trainee.setTrainingLevel(request.trainingLevel());
        traineeRepository.save(trainee);
        return ApiResponse.success("Trainee measurements completed successfully.");
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

                String firstName = null;
                String lastName = null;
                boolean isMeasurementsSet = false;

                if (user instanceof Trainee trainee) {
                    firstName = trainee.getFirstName();
                    lastName = trainee.getLastName();
                    isMeasurementsSet = trainee.isMeasurementsSet();

                }

                LoginResponse response = new LoginResponse(
                        message,
                        token,
                        refreshTokenValue,
                        user.getEmail(),
                        user.getUsername(),
                        user.getRole().name(),
                        firstName,
                        lastName,
                        user.getGender(),
                        user.getBirthYear(),
                        isMeasurementsSet
                );

                return ApiResponse.success(message, response);
            } else {
                return ApiResponse.failure("Invalid username or password");
            }
        } catch (Exception e) {
            return ApiResponse.failure("Error logging in user: " + e.getMessage());
        }
    }


    @Override
    public Object logoutUser(String deviceToken) {
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
        return userRepository.existsByEmailIgnoreCase(email) ? ApiResponse.success(email + " is exists") : ApiResponse.failure(email + " is not exists");
    }

    @Override
    public Object existsByUsername(String username) {
        return userRepository.existsByUsernameIgnoreCase(username) ? ApiResponse.success(username + " is exists") : ApiResponse.failure(username + " is not exists");
    }


    @Override
    public Object refreshToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");

        if (!accessTokenService.exists(token)) {
            throw new InvalidTokenException("Invalid refresh token");
        }

        AccessToken oldToken = accessTokenService.get(token);
        if (oldToken.getExpiration().before(new Date())) {
            throw new ExpiredTokenException("Refresh token expired");
        }

        accessTokenService.delete(token);

        AccessToken newToken = accessTokenService.refresh(oldToken.getUser());

        return ApiResponse.of("Token refreshed", newToken.getToken());
    }


}


