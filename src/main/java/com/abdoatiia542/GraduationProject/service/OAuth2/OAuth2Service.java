//package com.abdoatiia542.GraduationProject.service.OAuth2;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//
//@Service
//public class OAuth2Service {
//
//    private final RestTemplate restTemplate;
//    private final ObjectMapper objectMapper;
//
//    @Value("${spring.security.oauth2.client.registration.google.client-id}")
//    private String clientId;
//
//    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
//    private String clientSecret;
//
//    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
//    private String redirectUri;
//
//    public OAuth2Service(RestTemplate restTemplate, ObjectMapper objectMapper) {
//        this.restTemplate = restTemplate;
//        this.objectMapper = objectMapper;
//    }
//
//    public AccessTokenResponse getOauthAccessTokenGoogle(String code) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("code", code);
//        params.add("client_id", clientId);
//        params.add("client_secret", clientSecret);
//        params.add("redirect_uri", redirectUri);
//        params.add("grant_type", "authorization_code");
//        // دمج الـ scopes في قيمة واحدة مفصولة بمسافات
//        params.add("scope", "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email openid");
//
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
//
//        String url = "https://oauth2.googleapis.com/token";
//        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
//
//        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//            try {
//                return objectMapper.readValue(response.getBody(), AccessTokenResponse.class);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to parse access token response", e);
//            }
//        } else {
//            throw new RuntimeException("Failed to obtain access token: " + response.getStatusCode());
//        }
//    }
//
//
//    public static class AccessTokenResponse {
//        private String access_token;
//        private String token_type;
//        private long expires_in;
//        private String refresh_token;
//        private String scope;
//
//        public String getAccessToken() {
//            return access_token;
//        }
//
//        public void setAccessToken(String access_token) {
//            this.access_token = access_token;
//        }
//
//        public String getTokenType() {
//            return token_type;
//        }
//
//        public void setTokenType(String token_type) {
//            this.token_type = token_type;
//        }
//
//        public long getExpiresIn() {
//            return expires_in;
//        }
//
//        public void setExpiresIn(long expires_in) {
//            this.expires_in = expires_in;
//        }
//
//        public String getRefreshToken() {
//            return refresh_token;
//        }
//
//        public void setRefreshToken(String refresh_token) {
//            this.refresh_token = refresh_token;
//        }
//
//        public String getScope() {
//            return scope;
//        }
//
//        public void setScope(String scope) {
//            this.scope = scope;
//        }
//    }
//}