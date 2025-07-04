package com.abdoatiia542.GraduationProject.service.progress;

import com.abdoatiia542.GraduationProject.handler.NotFoundException;
import com.abdoatiia542.GraduationProject.model.plan.Exercise;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.plan.Exercise;
import com.abdoatiia542.GraduationProject.model.plan.WorkoutSessions;
import com.abdoatiia542.GraduationProject.model.progress.DailyProgress;
import com.abdoatiia542.GraduationProject.repository.workouts.ExerciseRepository;
import com.abdoatiia542.GraduationProject.repository.progress.DailyProgressRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.ExerciseRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.WorkoutSessionsRepository;
import com.abdoatiia542.GraduationProject.service.trainee.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.abdoatiia542.GraduationProject.dto.progress.DailyProgressDto;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyProgressService {

    private final DailyProgressRepository progressRepository;
    private final ExerciseRepository exerciseRepository;
    private final TraineeService traineeService;
    private final WorkoutSessionsRepository workoutSessionsRepository;


    public int completeExerciseAndTrackProgress(Integer exerciseId) {
        Trainee trainee = traineeService.getCurrentTrainee();
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new NotFoundException("Exercise with ID " + exerciseId + " not found."));

        int calories = (exercise.getTotalCalories() != null) ? exercise.getTotalCalories() : 0;
        int duration = (exercise.getDurationSeconds() != null) ? exercise.getDurationSeconds() : 0;

        // 1 exercise only
        addProgressToToday(trainee, calories, 1, duration);

        return calories;
    }
    public int completeWorkoutSessionAndTrackProgress(Integer sessionId) {
        Trainee trainee = traineeService.getCurrentTrainee();
        WorkoutSessions session = workoutSessionsRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException("Workout session with ID " + sessionId + " not found."));

        int totalCalories = 0;
        int totalDuration = 0;
        int totalExercises = session.getExercises().size();

        for (Exercise ex : session.getExercises()) {
            totalCalories += (ex.getTotalCalories() != null) ? ex.getTotalCalories() : 0;
            totalDuration += (ex.getDurationSeconds() != null) ? ex.getDurationSeconds() : 0;
        }

        addProgressToToday(trainee, totalCalories, totalExercises, totalDuration);
        return totalCalories;
    }


    public int getTodayBurnedCalories() {
        Trainee trainee = traineeService.getCurrentTrainee();
        return progressRepository.findByDateAndTrainee(LocalDate.now(), trainee)
                .map(DailyProgress::getTotalBurnedCalories)
                .orElse(0);
    }
    private void addProgressToToday(Trainee trainee, int calories, int exercisesCount, int trainingDurationSeconds) {
        LocalDate today = LocalDate.now();

        DailyProgress progress = progressRepository
                .findByDateAndTrainee(today, trainee)
                .orElse(DailyProgress.builder()
                        .trainee(trainee)
                        .date(today)
                        .totalBurnedCalories(0)
                        .totalExercisesCount(0)
                        .totalTrainingSeconds(0)
                        .build());


        int oldCalories = progress.getTotalBurnedCalories() != null ? progress.getTotalBurnedCalories() : 0;
        int oldExercises = progress.getTotalExercisesCount() != null ? progress.getTotalExercisesCount() : 0;
        int oldDuration = progress.getTotalTrainingSeconds() != null ? progress.getTotalTrainingSeconds() : 0;

        progress.setTotalBurnedCalories(oldCalories + calories);
        progress.setTotalExercisesCount(oldExercises + exercisesCount);
        progress.setTotalTrainingSeconds(oldDuration + trainingDurationSeconds);

        progressRepository.save(progress);
    }


    public DailyProgressDto getProgressByDate(LocalDate date) {
        Trainee trainee = traineeService.getCurrentTrainee();

        DailyProgress progress = progressRepository.findByDateAndTrainee(date, trainee)
                .orElseThrow(() -> new NotFoundException("No progress found for date: " + date));

        return DailyProgressDto.builder()
                .date(progress.getDate())
                .totalBurnedCalories(progress.getTotalBurnedCalories() != null ? progress.getTotalBurnedCalories() : 0)
                .totalExercisesCount(progress.getTotalExercisesCount() != null ? progress.getTotalExercisesCount() : 0)
                .totalTrainingSeconds(progress.getTotalTrainingSeconds() != null ? progress.getTotalTrainingSeconds() : 0)
                .build();
    }



}
