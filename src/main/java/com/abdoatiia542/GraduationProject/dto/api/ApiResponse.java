package com.abdoatiia542.GraduationProject.dto.api;

public record ApiResponse(
        boolean success,
        String message,
        Object data
) {

    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(true, message, data);
    }

    public static ApiResponse success(String message) {
        return new ApiResponse(true, message, null);
    }

    public static ApiResponse failure(String message) {
        return new ApiResponse(false, message, null);
    }

    public static ApiResponse of(String message, Object data) {
        return new ApiResponse(true, message, data);
    }

    public static ApiResponse of(String message) {
        return new ApiResponse(true, message, "");
    }


}