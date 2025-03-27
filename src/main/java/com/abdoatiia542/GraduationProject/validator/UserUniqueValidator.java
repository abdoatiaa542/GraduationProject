package com.abdoatiia542.GraduationProject.validator;

import com.abdoatiia542.GraduationProject.annotations.UserUnique;
import com.abdoatiia542.GraduationProject.model.enumerations.UserUniqueConstraint;
import com.abdoatiia542.GraduationProject.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class UserUniqueValidator implements ConstraintValidator<UserUnique, String> {
    private UserUniqueConstraint uniqueConstraint;
    private final UserRepository repository;

    @Override
    public void initialize(UserUnique constraintAnnotation) {
        uniqueConstraint = constraintAnnotation.constraint();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.isNull(value) ||
                switch (uniqueConstraint) {
                    case EMAIL -> !repository.existsByEmailIgnoreCase(value.strip());
                    case USERNAME -> !repository.existsByUsernameIgnoreCase(value.strip());
                };
    }
}