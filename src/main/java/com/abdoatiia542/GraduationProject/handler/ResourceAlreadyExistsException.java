package com.abdoatiia542.GraduationProject.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(Class theClass, List<String> args) {
        throw new RuntimeException(theClass.getSimpleName()+" with fields [%s]".formatted(String.join(", ", args)) + " Already exists");
    }

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

}
