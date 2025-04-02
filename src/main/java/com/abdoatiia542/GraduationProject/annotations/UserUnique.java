package com.abdoatiia542.GraduationProject.annotations;

import com.abdoatiia542.GraduationProject.model.enumerations.UserUniqueConstraint;
import com.abdoatiia542.GraduationProject.validator.UserUniqueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
* login  >>  deviceToken
*
* */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Constraint(validatedBy = UserUniqueValidator.class)
public @interface UserUnique {
    UserUniqueConstraint constraint();

    String message() default "this value is already used";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}