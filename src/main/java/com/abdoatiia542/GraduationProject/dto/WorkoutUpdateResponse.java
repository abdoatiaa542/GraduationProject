package com.abdoatiia542.GraduationProject.dto;

import java.util.List;

public record WorkoutUpdateResponse(
        Integer id,
        String name,
        String description,
        String trainingLevel,
        String image,
        List<SimpleExerciseResponse> exercises
) {}