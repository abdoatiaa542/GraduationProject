package com.abdoatiia542.GraduationProject.filter;

import com.abdoatiia542.GraduationProject.constant.JwtConstants;
import com.abdoatiia542.GraduationProject.dto.ApiResponse;
import com.abdoatiia542.GraduationProject.model.AccessToken;
import com.abdoatiia542.GraduationProject.model.User;
import com.abdoatiia542.GraduationProject.service.access_token.IAccessTokenService;
import com.abdoatiia542.GraduationProject.service.jwt.BearerTokenWrapper;
import com.abdoatiia542.GraduationProject.service.jwt.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IAccessTokenService accessTokenService;
    private final JwtService jwtService;
    private final BearerTokenWrapper bearerTokenWrapper;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isValidHeader(authorizationHeader)) {

            try {
                String jwt = extractJwt(authorizationHeader);
                if (jwtService.isValidToken(jwt)) {

                    AccessToken accessToken = accessTokenService.get(jwt);
                    User user = accessToken.getUser();

                    if (!user.isEnabled()) {
                        throw new AccessDeniedException("Account is not activated yet.");
                    }

                    if (!user.isAccountNonLocked()) {
                        throw new AccessDeniedException("Account is locked by moderators.");
                    }

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user, null, user.getAuthorities()
                            );
                    authentication.setDetails(new WebAuthenticationDetails(request));

                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    securityContext.setAuthentication(authentication);

                    SecurityContextHolder.setContext(securityContext);

                    bearerTokenWrapper.setToken(jwt);
                }
            } catch (Exception exception) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                ApiResponse apiResponse = new ApiResponse(false, exception.getMessage(), "");
                new ObjectMapper().writeValue(response.getWriter(), apiResponse);

                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private static String extractJwt(String authorizationHeader) {
        return authorizationHeader.substring(JwtConstants.JWT_HEADER_PREFIX.length());
    }

    private static boolean isValidHeader(String authorizationHeader) {
        return Objects.nonNull(authorizationHeader) &&
                authorizationHeader.startsWith(JwtConstants.JWT_HEADER_PREFIX);
    }
}