package com.abdoatiia542.GraduationProject.handler;


public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException(String message) {
        super(message);
    }
}
