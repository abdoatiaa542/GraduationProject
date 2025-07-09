package com.abdoatiia542.GraduationProject.customizer;

import com.abdoatiia542.GraduationProject.model.User;
import com.abdoatiia542.GraduationProject.model.enumerations.Role;
import com.abdoatiia542.GraduationProject.repository.UserRepository;
import com.abdoatiia542.GraduationProject.service.jwt.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    private static final long EXPIRATION = 1000 * 60 * 60 * 24 * 7;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        if (email == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Email not found in OAuth2 response");
            return;
        }

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .email(email)
                            .username(generateUsername(email))
                            .role(Role.TRAINEE)
                            .enabled(true)
                            .password(null)
                            .build();
                    return userRepository.save(newUser);
                });
        String token = jwtService.generateToken(user, EXPIRATION);

        response.sendRedirect("http://localhost:8080/login/oauth2/code/google" + token);


    }

    private String generateUsername(String email) {
        return email.split("@")[0] + System.currentTimeMillis();
    }
}
