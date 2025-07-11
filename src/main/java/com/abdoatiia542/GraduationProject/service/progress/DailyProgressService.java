package com.abdoatiia542.GraduationProject.service.progress;

import com.abdoatiia542.GraduationProject.dto.api.ApiResponse;
import com.abdoatiia542.GraduationProject.dto.progress.DailyProgressDto;
import com.abdoatiia542.GraduationProject.handler.NotFoundException;
import com.abdoatiia542.GraduationProject.mapper.DailyProgressMapper;
import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.exercises.Exercise;
import com.abdoatiia542.GraduationProject.model.exercises.WorkoutSessions;
import com.abdoatiia542.GraduationProject.model.food.CompletedMeal;
import com.abdoatiia542.GraduationProject.model.food.Meal;
import com.abdoatiia542.GraduationProject.model.food.MealPlan;
import com.abdoatiia542.GraduationProject.model.progress.DailyProgress;
import com.abdoatiia542.GraduationProject.repository.food.CompletedMealRepository;
import com.abdoatiia542.GraduationProject.repository.food.MealPlanRepository;
import com.abdoatiia542.GraduationProject.repository.food.MealRepository;
import com.abdoatiia542.GraduationProject.repository.progress.DailyProgressRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.ExerciseRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.WorkoutSessionsRepository;
import com.abdoatiia542.GraduationProject.service.trainee.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyProgressService {

    private final DailyProgressRepository progressRepository;
    private final ExerciseRepository exerciseRepository;
    private final TraineeService traineeService;
    private final WorkoutSessionsRepository workoutSessionsRepository;
    private final MealRepository mealRepository;
    private final MealPlanRepository mealPlanRepository;
    private final CompletedMealRepository completedMealRepository;

    // ✅ تسجيل تمرين فردي
    public ApiResponse completeExerciseAndTrackProgress(Integer exerciseId , Integer exerciseDuration) {
        Trainee trainee = traineeService.getCurrentTrainee();
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new NotFoundException("Exercise with ID " + exerciseId + " not found."));

        int calories = (exercise.getTotalCalories() != null) ? exercise.getTotalCalories() : 0;
        int duration = exercise.getDurationSeconds() == null ? exerciseDuration : exercise.getDurationSeconds();

        addProgressToToday(trainee, calories, 1, duration);
        return ApiResponse.of("Exercise progress recorded for today.", Map.of("burnedCalories", calories));
    }

    // ✅ تسجيل جلسة تمرينات كاملة
    public ApiResponse completeWorkoutSessionAndTrackProgress(Integer sessionId , Integer workoutDuration) {
        Trainee trainee = traineeService.getCurrentTrainee();
        WorkoutSessions session = workoutSessionsRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException("Workout session with ID " + sessionId + " not found."));

        int totalCalories = 0;
        int totalDuration = workoutDuration;
        int totalExercises = session.getExercises().size();

        for (Exercise ex : session.getExercises()) {
            totalCalories += (ex.getTotalCalories() != null) ? ex.getTotalCalories() : 0;
        }

        addProgressToToday(trainee, totalCalories, totalExercises, totalDuration);
        return ApiResponse.of("Exercise progress recorded for today.", Map.of("burnedCalories", totalCalories));
    }

    // ✅ استرجاع السعرات المحروقة اليوم
    public int getTodayBurnedCalories() {
        Trainee trainee = traineeService.getCurrentTrainee();
        return progressRepository.findByDateAndTrainee(LocalDate.now(), trainee)
                .map(DailyProgress::getTotalBurnedCalories)
                .orElse(0);
    }

    // ✅ تحديث التقدم الرياضي لليوم الحالي
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
                        .consumedCalories(0.0)
                        .consumedCarbs(0.0)
                        .consumedFat(0.0)
                        .consumedProtein(0.0)
                        .build());

        int oldCalories = progress.getTotalBurnedCalories() != null ? progress.getTotalBurnedCalories() : 0;
        int oldExercises = progress.getTotalExercisesCount() != null ? progress.getTotalExercisesCount() : 0;
        int oldDuration = progress.getTotalTrainingSeconds() != null ? progress.getTotalTrainingSeconds() : 0;

        progress.setTotalBurnedCalories(oldCalories + calories);
        progress.setTotalExercisesCount(oldExercises + exercisesCount);
        progress.setTotalTrainingSeconds(oldDuration + trainingDurationSeconds);

        progressRepository.save(progress);
    }

    // ✅ استرجاع التقدم في يوم محدد
    public ApiResponse getProgressByDate(LocalDate date) {
        Trainee trainee = traineeService.getCurrentTrainee();

        // بدل throw هنا
        DailyProgress progress = progressRepository.findByDateAndTrainee(date, trainee)
                .orElse(DailyProgress.builder()
                        .trainee(trainee)
                        .date(date)
                        .totalBurnedCalories(0)
                        .totalExercisesCount(0)
                        .totalTrainingSeconds(0)
                        .consumedCalories(0.0)
                        .consumedCarbs(0.0)
                        .consumedFat(0.0)
                        .consumedProtein(0.0)
                        .build());

        MealPlan plan = mealPlanRepository.findTopByTraineeOrderByIdDesc(trainee)
                .orElseThrow(() -> new NotFoundException("No meal plan found for this trainee."));

        double calRatio = (plan.getTotalCalories() > 0) ? (progress.getConsumedCalories() / plan.getTotalCalories()) * 100 : 0;
        double carbRatio = (plan.getTotalCarbs() > 0) ? (progress.getConsumedCarbs() / plan.getTotalCarbs()) * 100 : 0;
        double fatRatio = (plan.getTotalFat() > 0) ? (progress.getConsumedFat() / plan.getTotalFat()) * 100 : 0;
        double proteinRatio = (plan.getTotalProtein() > 0) ? (progress.getConsumedProtein() / plan.getTotalProtein()) * 100 : 0;

        Map<String, Object> response = Map.of(
                "exerciseProgress", Map.of(
                        "totalBurnedCalories", progress.getTotalBurnedCalories(),
                        "totalExercisesCount", progress.getTotalExercisesCount(),
                        "totalTrainingSeconds", progress.getTotalTrainingSeconds()
                ),
                "nutritionProgress", Map.of(
                        "caloriesRatio", String.format("%.2f", calRatio) + "%",
                        "carbsRatio", String.format("%.2f", carbRatio) + "%",
                        "fatRatio", String.format("%.2f", fatRatio) + "%",
                        "proteinRatio", String.format("%.2f", proteinRatio) + "%"
                )
        );

        return ApiResponse.of("Progress Retrieved Successfully", response);
    }


    // ✅ تسجيل تناول وجبة غذائية
    public ApiResponse completeMeal(Long mealId) {
        Trainee trainee = traineeService.getCurrentTrainee();

        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new NotFoundException("Meal with ID " + mealId + " not found."));

        LocalDate today = LocalDate.now();

        // ✅ Check if meal already completed today
        boolean alreadyCompleted = completedMealRepository.existsByTraineeAndMealAndDate(trainee, meal, today);
        if (alreadyCompleted) {
            return ApiResponse.of("Meal already completed today.", Map.of());
        }

        // ✅ Get or create progress for today
        DailyProgress progress = progressRepository
                .findByDateAndTrainee(today, trainee)
                .orElse(DailyProgress.builder()
                        .trainee(trainee)
                        .date(today)
                        .totalBurnedCalories(0)
                        .totalExercisesCount(0)
                        .totalTrainingSeconds(0)
                        .consumedCalories(0.0)
                        .consumedCarbs(0.0)
                        .consumedFat(0.0)
                        .consumedProtein(0.0)
                        .build());

        // ✅ Update progress
        progress.setConsumedCalories(progress.getConsumedCalories() + meal.getCalories());
        progress.setConsumedCarbs(progress.getConsumedCarbs() + meal.getCarbs());
        progress.setConsumedFat(progress.getConsumedFat() + meal.getFat());
        progress.setConsumedProtein(progress.getConsumedProtein() + meal.getProtein());

        progressRepository.save(progress);

        // ✅ Mark meal as completed
        CompletedMeal completedMeal = new CompletedMeal();
        completedMeal.setMeal(meal);
        completedMeal.setTrainee(trainee);
        completedMeal.setDate(today);
        completedMealRepository.save(completedMeal);

        // ✅ Calculate meal ratio
        MealPlan plan = mealPlanRepository.findTopByTraineeOrderByIdDesc(trainee)
                .orElseThrow(() -> new NotFoundException("No meal plan found for this trainee."));

        double calRatio = (plan.getTotalCalories() > 0) ? (meal.getCalories() / plan.getTotalCalories()) * 100 : 0;
        double carbRatio = (plan.getTotalCarbs() > 0) ? (meal.getCarbs() / plan.getTotalCarbs()) * 100 : 0;
        double fatRatio = (plan.getTotalFat() > 0) ? (meal.getFat() / plan.getTotalFat()) * 100 : 0;
        double proteinRatio = (plan.getTotalProtein() > 0) ? (meal.getProtein() / plan.getTotalProtein()) * 100 : 0;

        return ApiResponse.of("Meal Added Successfully.",
                Map.of(
                        "caloriesRatio", String.format("%.2f", calRatio) + "%",
                        "carbsRatio", String.format("%.2f", carbRatio) + "%",
                        "fatRatio", String.format("%.2f", fatRatio) + "%",
                        "proteinRatio", String.format("%.2f", proteinRatio) + "%"
                )
        );
    }

}



