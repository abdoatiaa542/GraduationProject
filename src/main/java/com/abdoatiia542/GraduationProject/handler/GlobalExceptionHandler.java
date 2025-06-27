package com.abdoatiia542.GraduationProject.handler;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.function.Function;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Function<Exception, ApiResponse> function =
            exception -> new ApiResponse(false, exception.getLocalizedMessage(), exception.getCause());

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException exception, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        exception.printStackTrace();
        return ResponseEntity.status(status).body(new ApiResponse(false, exception.getMessage().split(":")[0], ""));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException exception, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        List<String> data = exception.getFieldErrors().stream().map(i -> "%s: %s".formatted(i.getField(), i.getDefaultMessage())).toList();
        return ResponseEntity.status(status).body(new ApiResponse(false, "One or more fields is Invalid.", data));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(@NonNull MissingServletRequestParameterException exception, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        return ResponseEntity.status(status).body(function.apply(exception));
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(@NonNull HandlerMethodValidationException exception, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        return ResponseEntity.status(status).body(new ApiResponse(false, "Missing or invalid Request Parameter(s).", exception.getDetailMessageArguments()));
    }

    @ExceptionHandler(value = AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthenticationException(@NotNull AuthenticationException exception) {
        return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(function.apply(exception));
    }

    @ExceptionHandler(value = DataAccessException.class)
    protected ResponseEntity<Object> handleDataAccessException(@NotNull DataAccessException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(function.apply(exception));
    }

    @ExceptionHandler(value = PersistenceException.class)
    protected ResponseEntity<Object> handlePersistenceException(@NotNull PersistenceException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(function.apply(exception));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ex.printStackTrace();
        return super.handleHttpMessageNotWritable(ex, headers, status, request);
    }

    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(@NotNull RuntimeException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(function.apply(exception));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(@NotNull IllegalArgumentException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(function.apply(exception));
    }

    @ExceptionHandler(value = IllegalStateException.class)
    protected ResponseEntity<Object> handleIllegalStateException(@NotNull IllegalStateException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(function.apply(exception));
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(@NotNull AccessDeniedException exception) {
        return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(function.apply(exception));
    }

    @ExceptionHandler(value = UnsupportedOperationException.class)
    protected ResponseEntity<Object> handleUnsupportedOperationException(@NotNull UnsupportedOperationException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(function.apply(exception));
    }


}