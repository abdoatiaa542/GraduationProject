package com.abdoatiia542.GraduationProject.dto.workoutResponse;

import com.abdoatiia542.GraduationProject.model.enumerations.TrainingLevel;
import org.springframework.web.multipart.MultipartFile;


public record WorkoutMediaUpdateRequest(
        String name,
        String description,
        TrainingLevel trainingLevel,
        MultipartFile workoutImage

) {
}