package com.abdoatiia542.GraduationProject.model.enumerations;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Goal {
    LOSE_WEIGHT,
   MUSCLE_GAIN,
    STAY_FIT;

    @JsonCreator
    public static Goal from(String value) {
        return Goal.valueOf(value.toUpperCase());
    }
}
