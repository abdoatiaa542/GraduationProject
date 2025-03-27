package com.abdoatiia542.GraduationProject.service.auth;

import com.abdoatiia542.GraduationProject.dto.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.LoginRequest;
import com.abdoatiia542.GraduationProject.dto.TraineeRegistrationRequest;
import com.abdoatiia542.GraduationProject.exception.ResourceAlreadyExistsException;
import com.abdoatiia542.GraduationProject.mapper.TraineeRegistrationRequestMapper;
import com.abdoatiia542.GraduationProject.model.AccessToken;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.User;
import com.abdoatiia542.GraduationProject.repository.TraineeRepository;
import com.abdoatiia542.GraduationProject.repository.UserRepository;
import com.abdoatiia542.GraduationProject.service.access_token.AccessTokenService;
import com.abdoatiia542.GraduationProject.service.db.DatabaseService;
import com.abdoatiia542.GraduationProject.service.jwt.BearerTokenWrapper;
import com.abdoatiia542.GraduationProject.utils.ContextHolderUtils;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final BearerTokenWrapper bearerTokenWrapper;
    private final AccessTokenService accessTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TraineeRepository traineeRepository;
    private final PasswordEncoder passwordEncoder;
    private final TraineeRegistrationRequestMapper traineeRegistrationRequestMapper;

    @Override
    public Object registerTrainee(TraineeRegistrationRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new ResourceAlreadyExistsException("User with this email already exists");
        }
        Trainee trainee = traineeRegistrationRequestMapper.apply(request);
        return traineeRepository.save(trainee);
    }

    @Override
    public Object loginUser(Object object) {
        LoginRequest request = (LoginRequest) object;
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        if (Objects.nonNull(authentication) &&
                authentication.isAuthenticated() &&
                authentication.getPrincipal()
                        instanceof User user) {

            AccessToken accessToken = accessTokenService.create(user);

            if (Strings.hasText(request.deviceToken())) {
                user.getDeviceTokens().add(request.deviceToken().strip());
                userRepository.save(user);
            }

            String response = accessToken.getToken();
            String message = "Successful user login.";

            return ApiResponse.of(message, response);
        }

        throw new UsernameNotFoundException("Invalid username or password");
    }

    @Override
    public Object logoutUser(String deviceToken) {
        String bearerToken = bearerTokenWrapper.getToken();
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
