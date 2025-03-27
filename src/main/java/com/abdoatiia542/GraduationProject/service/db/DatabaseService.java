package com.abdoatiia542.GraduationProject.service.db;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import org.assertj.core.util.TriFunction;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class DatabaseService {

    public static <T, R> R get(Function<T, Optional<R>> function, T value, Class<?> theClass) {
        return function.apply(value).orElseThrow(
                () -> new EntityNotFoundException("Entity %s with identifier %s not found!".formatted(theClass.getSimpleName(), value)));
    }

    public static <T, U, R> R get(BiFunction<T, U, Optional<R>> function, T value1, U value2, Class<?> theClass) {
        return function.apply(value1, value2).orElseThrow(
                () -> new EntityNotFoundException("Entity %s with identifier %s and %s not found!".formatted(theClass.getSimpleName(), value1, value2)));
    }

    public static <T, U, V, R> R get(TriFunction<T, U, V, Optional<R>> function, T value1, U value2, V value3, Class<?> theClass) {
        return function.apply(value1, value2, value3).orElseThrow(
                () -> new EntityNotFoundException("Entity %s with identifier %s, %s and %s not found!".formatted(theClass.getSimpleName(), value1, value2, value3)));
    }


    public static <T> void unique(Predicate<T> predicate, T value, Class<?> theClass) {
        if (predicate.test(value))
            throw new EntityExistsException("Entity %s with identifier %s already exists!".formatted(theClass.getSimpleName(), value));
    }

    public static <T, U> void unique(BiPredicate<T, U> predicate, T value1, U value2, Class<?> theClass) {
        if (predicate.test(value1, value2))
            throw new EntityExistsException("Entity %s with identifier %s and %s already exists!"
                    .formatted(theClass.getSimpleName(), value1, value2));
    }

    public static <T, U> void exist(BiPredicate<T, U> predicate, T value1, U value2, Class<?> theClass) {
        if (predicate.test(value1, value2)) return;
        throw new EntityNotFoundException("Entity %s with identifier %s and %s not found!"
                .formatted(theClass.getSimpleName(), value1, value2));
    }

    public static <R> R get(Optional<R> optional, Class<?> theClass) {
        return optional.orElseThrow(
                () -> new EntityNotFoundException("Entity %s not found!".formatted(theClass.getSimpleName())));
    }
}