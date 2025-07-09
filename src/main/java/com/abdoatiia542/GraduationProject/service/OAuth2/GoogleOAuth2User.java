package com.abdoatiia542.GraduationProject.service.OAuth2;

import java.util.Map;

public class GoogleOAuth2User extends OAuth2UserDetails {


    public GoogleOAuth2User(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}
