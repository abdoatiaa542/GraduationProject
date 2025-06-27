package com.abdoatiia542.GraduationProject.utils.ai_assistat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class AIAssistantUtils {

    private static RestTemplate restTemplate = new RestTemplate();

    public static @NotNull ResponseEntity<String> getStringResponseEntity(String apiUrl, HttpEntity<MultiValueMap<String, Object>> request) {
        ResponseEntity<String> response;
        try {
            response = restTemplate.postForEntity(apiUrl, request, String.class);
        } catch (HttpClientErrorException ex) {
            String responseBody = ex.getResponseBodyAsString();
            Map<String, Object> responseBodyMap;
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                responseBodyMap = objectMapper.readValue(responseBody, Map.class);
            } catch (Exception e) {
                throw new RuntimeException("Error occurred, but response could not be parsed: " + responseBody, e);
            }

            String errorMessage = responseBodyMap.getOrDefault("detail", "Unknown error").toString();

            throw new RuntimeException(errorMessage);
        }
        return response;
    }
}
