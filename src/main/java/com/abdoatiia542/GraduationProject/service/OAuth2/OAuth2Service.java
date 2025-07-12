package com.abdoatiia542.GraduationProject.service.OAuth2;

import com.abdoatiia542.GraduationProject.dto.LoginResponse;
import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.model.AccessToken;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.User;
import com.abdoatiia542.GraduationProject.model.enumerations.Role;
import com.abdoatiia542.GraduationProject.repository.UserRepository;
import com.abdoatiia542.GraduationProject.service.access_token.AccessTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
public class OAuth2Service {

    private static final String TOKEN_URL = "https://oauth2.googleapis.com/token";
    private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final AccessTokenService accessTokenService;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    public OAuth2Service(RestTemplate restTemplate, ObjectMapper objectMapper, UserRepository userRepository, AccessTokenService accessTokenService) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.accessTokenService = accessTokenService;
    }


    public ApiResponse authenticateWithGoogle(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<Map> tokenResponse = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, requestEntity, Map.class);
        String accessToken = (String) tokenResponse.getBody().get("access_token");

        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.setBearerAuth(accessToken);
        HttpEntity<Void> userInfoRequest = new HttpEntity<>(userInfoHeaders);

        ResponseEntity<Map> userInfoResponse = restTemplate.exchange(USER_INFO_URL, HttpMethod.GET, userInfoRequest, Map.class);
        Map<String, Object> userInfo = userInfoResponse.getBody();
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");
        String picture = (String) userInfo.get("picture");

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            return ApiResponse.of(" User already exists with email ");
        }


        User user = new User();
        user.setEmail(email);
        user.setUsername(name);
        user.setImage(picture);
        user.setRole(Role.TRAINEE);
        userRepository.save(user);

        AccessToken token = accessTokenService.create(user);
        AccessToken refreshToken = accessTokenService.refresh(user);
        String firstName = null;
        String lastName = null;
        boolean isMeasurementsSet = false;

        if (user instanceof Trainee trainee) {
            firstName = trainee.getFirstName();
            lastName = trainee.getLastName();
            isMeasurementsSet = trainee.isMeasurementsSet();
        }

        LoginResponse response = new LoginResponse("User logged in successfully with Google", token.getToken(), refreshToken.getToken(), user.getEmail(), user.getUsername(), user.getRole().name(), firstName, lastName, user.getGender(), user.getBirthYear(), isMeasurementsSet);

        return ApiResponse.success("Login with Google successful", response);
    }

}
