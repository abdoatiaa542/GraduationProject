package com.abdoatiia542.GraduationProject.model.enumerations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ActivityLevel {
    ACTIVE("Active"),
    SEDENTARY("Sedentary"),
    LIGHTLY_ACTIVE("Lightly Active"),
    MODERATELY_ACTIVE("Moderately Active"),
    VERY_ACTIVE("Very Active");


    private final String value;

    ActivityLevel(String value) {
        this.value = value;
    }


    @JsonValue
    public String getValue() {
        return value;
    }


    @JsonValue
    public String toJson() {
        // Capitalize first letter, lowercase the rest
        String original = name().toLowerCase();
        return Character.toUpperCase(original.charAt(0)) + original.substring(1);
    }

    private static final Map<String, ActivityLevel> VALUE_MAP = Stream.of(values())
            .collect(Collectors.toMap(
                    a -> a.value.toLowerCase(), // lowercase for flexible matching
                    a -> a
            ));

    @JsonCreator
    public static ActivityLevel from(String value) {
        return ActivityLevel.valueOf(value.toUpperCase().replace(" ", "_"));
    }
}
