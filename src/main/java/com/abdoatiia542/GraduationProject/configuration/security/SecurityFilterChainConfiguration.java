package com.abdoatiia542.GraduationProject.configuration.security;

import com.abdoatiia542.GraduationProject.customizer.CorsCustomizer;
import com.abdoatiia542.GraduationProject.customizer.CustomOAuth2SuccessHandler;
import com.abdoatiia542.GraduationProject.customizer.ExceptionHandlingCustomizer;
import com.abdoatiia542.GraduationProject.customizer.SessionManagementCustomizer;
import com.abdoatiia542.GraduationProject.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomOAuth2SuccessHandler customOAuth2SuccessHandler) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .cors(new CorsCustomizer())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/api/v1/auth/**"
//                                "/api/v1/my-account/**"
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(new SessionManagementCustomizer())
                .exceptionHandling(new ExceptionHandlingCustomizer())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth -> oauth.successHandler(customOAuth2SuccessHandler))
                .build();
    }


}