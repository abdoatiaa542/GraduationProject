package com.abdoatiia542.GraduationProject.model.enumerations;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");


    private final String value;

    Gender(String value) {
        this.value = value;
    }


    @JsonValue
    public String getValue() {
        return value;
    }
}