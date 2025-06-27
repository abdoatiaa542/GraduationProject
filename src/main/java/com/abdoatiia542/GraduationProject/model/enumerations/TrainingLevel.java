package com.abdoatiia542.GraduationProject.model.enumerations;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TrainingLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED;

    @JsonCreator
    public static TrainingLevel from(String value) {
        return TrainingLevel.valueOf(value.toUpperCase());
    }
}
