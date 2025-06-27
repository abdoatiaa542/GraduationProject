package com.abdoatiia542.GraduationProject.model.enumerations;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ActivityLevel {
    ACTIVE,
    SEDENTARY,
    LIGHTLY_ACTIVE,
    MODERATELY_ACTIVE,
    VERY_ACTIVE;

    @JsonCreator
    public static ActivityLevel from(String value) {
        return ActivityLevel.valueOf(value.toUpperCase());
    }
}
