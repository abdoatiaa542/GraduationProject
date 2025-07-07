package com.abdoatiia542.GraduationProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseUploadRequestDto {
    private String name;
    private String description;
    MultipartFile exerciseImage;
    MultipartFile exerciseVideo;
    private String reps;
    private Integer sets;
    private Integer durationSeconds;
    private Integer durationRestSeconds;
    private Integer caloriesBurned;
    private Integer totalCalories;
    private Set<String> bodyFocuses;
}
