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

    MultipartFile exerciseImage;
    MultipartFile exerciseVideo;

}
