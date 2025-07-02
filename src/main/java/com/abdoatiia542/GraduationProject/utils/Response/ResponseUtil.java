package com.abdoatiia542.GraduationProject.utils.Response;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

public class ResponseUtil {

    private ResponseUtil() {
        // Utility class - no need to instantiate
    }
    public static ResponseEntity<ApiResponse> okOrNotFound(ApiResponse response) {
        return response.success() ?
                ResponseEntity.ok(response) :
                ResponseEntity.status(404).body(response);
    }
    public static <T> ResponseEntity<com.abdoatiia542.GraduationProject.dto.api.ApiResponse> buildListResponse(String message, List<T> data) {
        if (data == null || data.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(com.abdoatiia542.GraduationProject.dto.api.ApiResponse.failure("No data found"));
        }
        return ResponseEntity.ok(ApiResponse.success(message, data));
    }

    public static ResponseEntity<ApiResponse> buildSuccess(String message, Object data) {
        return ResponseEntity.ok(ApiResponse.success(message, data));
    }

    public static ResponseEntity<ApiResponse> buildFailure(String message, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(ApiResponse.failure(message));
    }

    public static ResponseEntity<ApiResponse> createdOrConflict(ApiResponse response, String locationPath) {
        return response.success()
                ? ResponseEntity.created(URI.create(locationPath)).body(response)
                : ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    public static ResponseEntity<ApiResponse> okOrBadRequest(ApiResponse response) {
        return response.success()
                ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    public static ResponseEntity<ApiResponse> okOrUnauthorized(ApiResponse response) {
        return response.success()
                ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    public static ResponseEntity<ApiResponse> accepted(ApiResponse response) {
        return ResponseEntity.accepted().body(response);
    }
}
