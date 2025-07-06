package com.abdoatiia542.GraduationProject.model.enumerations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Goal {
    LOSE_WEIGHT("Loss Weight"),
    MUSCLE_GAIN("Muscle Gain"),
    STAY_FIT("Stay Fit");

    private final String value;

    Goal(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Goal from(String value) {
        for (Goal goal : Goal.values()) {
            if (goal.value.equalsIgnoreCase(value)) {
                return goal;
            }
        }
        throw new IllegalArgumentException("Unknown goal: " + value);
    }
}
