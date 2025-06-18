package com.abdoatiia542.GraduationProject.service.access_token;

import com.abdoatiia542.GraduationProject.model.AccessToken;
import com.abdoatiia542.GraduationProject.model.User;
import org.springframework.stereotype.Service;

@Service
public interface IAccessTokenService {
    AccessToken create(User user);

    AccessToken get(String token);

    boolean exists(String token);

    void delete(String token);

    AccessToken refresh(User user);
}