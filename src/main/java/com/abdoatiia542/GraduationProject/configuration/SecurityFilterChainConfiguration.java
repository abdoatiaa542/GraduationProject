package com.abdoatiia542.GraduationProject.configuration;

import com.abdoatiia542.GraduationProject.customizer.AuthorizeHttpRequestsCustomizer;
import com.abdoatiia542.GraduationProject.customizer.CorsCustomizer;
import com.abdoatiia542.GraduationProject.customizer.ExceptionHandlingCustomizer;
import com.abdoatiia542.GraduationProject.customizer.SessionManagementCustomizer;
import com.abdoatiia542.GraduationProject.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfiguration {
    final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .cors(new CorsCustomizer())
                .authorizeHttpRequests(new AuthorizeHttpRequestsCustomizer())
                .sessionManagement(new SessionManagementCustomizer())
                .exceptionHandling(new ExceptionHandlingCustomizer())

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}