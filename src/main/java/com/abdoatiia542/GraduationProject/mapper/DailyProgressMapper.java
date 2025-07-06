package com.abdoatiia542.GraduationProject.mapper;

import com.abdoatiia542.GraduationProject.dto.progress.DailyProgressDto;
import com.abdoatiia542.GraduationProject.model.progress.DailyProgress;

public class DailyProgressMapper {



    public  static DailyProgressDto toDto (DailyProgress dailyProgress){
       return DailyProgressDto.builder()
                .date(dailyProgress.getDate())
                .totalBurnedCalories(dailyProgress.getTotalBurnedCalories() != null ? dailyProgress.getTotalBurnedCalories() : 0)
                .totalExercisesCount(dailyProgress.getTotalExercisesCount() != null ? dailyProgress.getTotalExercisesCount() : 0)
                .totalTrainingSeconds(dailyProgress.getTotalTrainingSeconds() != null ? dailyProgress.getTotalTrainingSeconds(): 0)
                .build();
    }
}
