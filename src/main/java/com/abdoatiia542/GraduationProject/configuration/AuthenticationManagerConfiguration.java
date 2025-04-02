package com.abdoatiia542.GraduationProject.configuration;

import com.abdoatiia542.GraduationProject.model.User;
import com.abdoatiia542.GraduationProject.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class AuthenticationManagerConfiguration implements AuthenticationManager {
    final UserDetailsService userDetailsService;
    final PasswordEncoder passwordEncoder;
    final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        Map<String, String> customResponse = new HashMap<>();

        if (!passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        if (!userDetails.isEnabled()) {
            String username = authentication.getName();
            User user = userRepository.findByUsernameOrEmail(username).
                    orElseThrow(() -> new RuntimeException("User not found"));
            customResponse.put("message", "The account is not activated yet.");
            customResponse.put("userId", user.getId().toString());  // Assuming MyUserDetails has getId() method
            customResponse.put("email", user.getEmail());
            String jsonResponse;
            try {
                jsonResponse = new ObjectMapper().writeValueAsString(customResponse);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            throw new DisabledException(jsonResponse);
        }

        if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("The account is locked by moderators.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}