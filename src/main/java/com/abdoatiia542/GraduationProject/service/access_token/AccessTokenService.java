package com.abdoatiia542.GraduationProject.service.access_token;


import com.abdoatiia542.GraduationProject.model.AccessToken;
import com.abdoatiia542.GraduationProject.model.User;
import com.abdoatiia542.GraduationProject.repository.AccessTokenRepository;
import com.abdoatiia542.GraduationProject.service.db.DatabaseService;
import com.abdoatiia542.GraduationProject.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccessTokenService implements IAccessTokenService {
    private final AccessTokenRepository repository;
    private final JwtService jwtService;

    @Override
    @Transactional
    public AccessToken create(User user) {
        String token = jwtService.generateToken(user, 1_000 * 60 * 60 * 24);
        Date expiration = jwtService.getExpiration(token);

        AccessToken accessToken = AccessToken
                .builder()
                .token(token)
                .expiration(expiration)
                .user(user)
                .build();

        AccessToken saved = repository.save(accessToken);
        return saved;
    }

    @Override
    @Transactional
    public AccessToken refresh(User user) {
        String token = jwtService.generateToken(user, 1_000L * 60 * 60 * 24 * 30);
        Date expiration = jwtService.getExpiration(token);

        AccessToken accessToken = AccessToken
                .builder()
                .token(token)
                .expiration(expiration)
                .user(user)
                .build();

        AccessToken saved = repository.save(accessToken);
        return saved;
    }


    @Override
    public AccessToken get(String token) {
        Optional<AccessToken> accessToken = repository.findById(token);
        if (accessToken.isEmpty()) {
            throw new IllegalArgumentException("Access token not found In Database");
        } else {
            return accessToken.get();
        }
    }

    @Override
    public boolean exists(String token) {
        return repository.existsById(token);
    }

    @Override
    public void delete(String token) {
        AccessToken accessToken = DatabaseService.get(repository::findById, token, AccessToken.class);
        repository.delete(accessToken);
    }
}