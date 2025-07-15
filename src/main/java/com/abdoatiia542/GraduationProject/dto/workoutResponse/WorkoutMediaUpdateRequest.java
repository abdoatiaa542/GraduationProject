package com.abdoatiia542.GraduationProject.dto.workoutResponse;

import org.springframework.web.multipart.MultipartFile;


public record WorkoutMediaUpdateRequest(

        MultipartFile workoutImage

) {
}