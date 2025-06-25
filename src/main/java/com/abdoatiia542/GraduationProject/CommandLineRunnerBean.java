package com.abdoatiia542.GraduationProject;

import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.enumerations.Gender;
import com.abdoatiia542.GraduationProject.model.enumerations.Role;
import com.abdoatiia542.GraduationProject.model.workout.*;
import com.abdoatiia542.GraduationProject.repository.UserRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.WorkoutPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class CommandLineRunnerBean {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final WorkoutPlanRepository workoutPlanRepository;

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, WorkoutPlanRepository workoutPlanRepository) {
        return args -> {
            log.info("Command line runner start");
            inserUser();

            if (workoutPlanRepository.count() == 0) {
                insertWorkoutPlans();
                log.info("Workout plans initialized successfully");
            } else {
                log.info("Workout plans already exist, skipping initialization");
            }
            log.info("CommandLineRunnerBean initialized successfully");
        };
    }

    void inserUser() {
        if (userRepository.count() == 0) {

            Trainee trainee1 = Trainee.builder()
                    .username("abdo")
                    .email("abdo@example.com")
                    .password(passwordEncoder.encode("123456"))
                    .role(Role.TRAINEE)
                    .gender(Gender.MALE)
                    .birthDate(LocalDate.of(2000, 1, 1))
                    .enabled(true)
                    .height(175.0)
                    .weight(75.0)
                    .targetWeight(70.0)
                    .build();

            Trainee trainee2 = Trainee.builder()
                    .username("amer")
                    .email("amer@example.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.TRAINEE)
                    .gender(Gender.FEMALE)
                    .birthDate(LocalDate.of(1998, 5, 10))
                    .enabled(true)
                    .height(165.0)
                    .weight(60.0)
                    .targetWeight(55.0)
                    .build();

            Trainee trainee3 = Trainee.builder()
                    .username("mohamed03")
                    .email("mohamed03@example.com")
                    .password(passwordEncoder.encode("pass1234"))
                    .role(Role.TRAINEE)
                    .gender(Gender.MALE)
                    .birthDate(LocalDate.of(1995, 3, 20))
                    .enabled(true)
                    .height(180.0)
                    .weight(90.0)
                    .targetWeight(80.0)
                    .build();

            userRepository.save(trainee1);
            userRepository.save(trainee2);
            userRepository.save(trainee3);

        }
    }

    private void insertWorkoutPlans() {
        // BMI Level 1 - Underweight
        createBMILevel1Plan();

        // BMI Level 2 - Normal Weight
        createBMILevel2Plan();

        // BMI Level 3 - Overweight
        createBMILevel3Plan();

        // BMI Level 4 - Obese Class I
        createBMILevel4Plan();

        // BMI Level 5 - Obese Class II
        createBMILevel5Plan();

        // BMI Level 6 - Obese Class III
        createBMILevel6Plan();

        // BMI Level 7 - Extreme Obesity
        createBMILevel7Plan();

        // BMI Level 8 - Morbid Obesity
        createBMILevel8Plan();


    }


    private void createBMILevel1Plan() {
        WorkoutPlan plan = WorkoutPlan.builder()
                .bmiLevel(1)
                .trainingSplit("Full Body")
                .goal("Build foundation, gain muscle")
                .trainingLevel(TrainingLevel.BEGINNER)
                .colorGradient("from-green-400 to-blue-500")
                .build();

        // Day 1
        WorkoutDay day1 = createWorkoutDay(plan, 1, "Day 1", false, null);
        addExercise(day1, "Bodyweight Squat", 2, "8-10", 60, "Proper form, deep as comfortable", "Leg & Butt", 1, 35, 70);
        addExercise(day1, "Incline Push Up", 2, "AMRAP", 60, "Hands elevated, modify if needed", "Chest Muscles", 2, 25, 50);
        addExercise(day1, "Bodyweight Assisted Pull Up", 2, "AMRAP", 60, "Use assistance, decrease over time", "Back Muscles", 3, 30, 60);
        addExercise(day1, "Hand Plank", 2, "20-30s hold", 60, "Straight line head to heels", "Six Pack", 4, 20, 40);
        addExercise(day1, "Bird Dog", 2, "10-12 per side", 60, "Engage core, balance", "Six Pack", 5, 25, 50);
        addExercise(day1, "Glute Bridge", 2, "12-15", 60, "Squeeze glutes", "Leg & Butt", 6, 30, 60);

        // Day 2 - Rest
        createWorkoutDay(plan, 2, "Day 2", true, "Rest or Light Activity");

        // Day 3
        WorkoutDay day3 = createWorkoutDay(plan, 3, "Day 3", false, null);
        addExercise(day3, "Bodyweight Squat", 2, "8-10", 60, "Controlled movements.", "Leg & Butt", 1, 35, 70);
        addExercise(day3, "Incline Push Up", 2, "AMRAP", 60, "", "Chest Muscles", 2, 25, 50);
        addExercise(day3, "Inverted Row", 2, "AMRAP", 60, "Sturdy table or TRX, lower slowly", "Back Muscles", 3, 28, 56);
        addExercise(day3, "Elbow Side Plank", 2, "20-30s hold per side", 60, "Straight line", "Six Pack", 4, 22, 44);
        addExercise(day3, "Supermans", 2, "10-12", 60, "Glutes and lower back", "Back Muscles", 5, 26, 52);
        addExercise(day3, "Laying Leg Raises", 2, "10-12", 60, "Lower back on ground", "Six Pack", 6, 24, 48);

        // Day 4 - Rest
        createWorkoutDay(plan, 4, "Day 4", true, "Rest or Light Activity");

        // Day 5 - Same as Day 1
        WorkoutDay day5 = createWorkoutDay(plan, 5, "Day 5", false, null);
        addExercise(day5, "Bodyweight Squat", 2, "8-10", 60, "Proper form, deep as comfortable", "Leg & Butt", 1, 35, 70);
        addExercise(day5, "Incline Push Up", 2, "AMRAP", 60, "Hands elevated, modify if needed", "Chest Muscles", 2, 25, 50);
        addExercise(day5, "Bodyweight Assisted Pull Up", 2, "AMRAP", 60, "Use assistance, decrease over time", "Back Muscles", 3, 30, 60);
        addExercise(day5, "Hand Plank", 2, "20-30s hold", 60, "Straight line head to heels", "Six Pack", 4, 20, 40);
        addExercise(day5, "Bird Dog", 2, "10-12 per side", 60, "Engage core, balance", "Six Pack", 5, 25, 50);
        addExercise(day5, "Glute Bridge", 2, "12-15", 60, "Squeeze glutes", "Leg & Butt", 6, 30, 60);

        // Day 6 & 7 - Rest
        createWorkoutDay(plan, 6, "Day 6", true, "Rest");
        createWorkoutDay(plan, 7, "Day 7", true, "Rest");

        // Add considerations
        plan.setConsiderations(List.of(
                PlanConsideration.builder().considerationText("Nutrition crucial, consult dietitian").plan(plan).build(),
                PlanConsideration.builder().considerationText("Medical guidance recommended").plan(plan).build(),
                PlanConsideration.builder().considerationText("Gradual progression").plan(plan).build(),
                PlanConsideration.builder().considerationText("Rest and recovery").plan(plan).build()
        ));

        // Save the plan
        workoutPlanRepository.save(plan);
    }

    private void createBMILevel2Plan() {
        WorkoutPlan plan = WorkoutPlan.builder()
                .bmiLevel(2)
                .trainingSplit("Full Body")
                .goal("Build strength & muscle, increase fitness")
                .trainingLevel(TrainingLevel.BEGINNER)
                .colorGradient("from-blue-400 to-purple-500")
                .build();

        // Day 1
        WorkoutDay day1 = createWorkoutDay(plan, 1, "Day 1", false, null);
        addExercise(day1, "Dumbbell Goblet Squat", 2, "8-10", 60, "Use appropriate weight", "Leg & Butt", 1, 40, 80);
        addExercise(day1, "Dumbbell Bench Press", 2, "8-10", 60, "Chest activation", "Chest Muscles", 2, 30, 60);
        addExercise(day1, "Dumbbell Row Unilateral", 2, "8-10 per side", 60, "Stable core, avoid twisting", "Back Muscles", 3, 32, 64);
        addExercise(day1, "Bodyweight Assisted Pull Up", 2, "AMRAP", 60, "Use assistance as needed", "Back Muscles", 4, 30, 60);
        addExercise(day1, "Hand Plank", 2, "30-45s hold", 60, "Straight line, core engaged", "Six Pack", 5, 22, 44);
        addExercise(day1, "Glute Bridge", 2, "12-15", 60, "Squeeze glutes", "Leg & Butt", 6, 30, 60);

        // Day 2 - Rest
        createWorkoutDay(plan, 2, "Day 2", true, "Rest or Light Activity (yoga, walking, stretching)");

        // Day 3
        WorkoutDay day3 = createWorkoutDay(plan, 3, "Day 3", false, null);
        addExercise(day3, "Dumbbell Romanian Deadlift", 2, "10-12", 60, "Hinge at hips, straight back", "Leg & Butt", 1, 38, 76);
        addExercise(day3, "Dumbbell Overhead Press", 2, "8-10", 60, "Control weight", "Shoulders", 2, 30, 60);
        addExercise(day3, "Inverted Row", 2, "AMRAP", 60, "Choose appropriate variation", "Back Muscles", 3, 28, 56);
        addExercise(day3, "Elbow Side Plank", 2, "30-45s hold per side", 60, "Straight line, core engaged", "Six Pack", 4, 24, 48);
        addExercise(day3, "Bird Dog", 2, "10-12 per side", 60, "Engage core, balance", "Six Pack", 5, 25, 50);
        addExercise(day3, "Dumbbell Curl", 2, "10-12", 60, "Controlled movements", "Biceps", 6, 26, 52);

        // Day 4 - Rest
        createWorkoutDay(plan, 4, "Day 4", true, "Rest or Light Activity");

        // Day 5 - Same as Day 1
        WorkoutDay day5 = createWorkoutDay(plan, 5, "Day 5", false, null);
        addExercise(day5, "Dumbbell Goblet Squat", 2, "8-10", 60, "Use appropriate weight", "Leg & Butt", 1, 40, 80);
        addExercise(day5, "Dumbbell Bench Press", 2, "8-10", 60, "Chest activation", "Chest Muscles", 2, 30, 60);
        addExercise(day5, "Dumbbell Row Unilateral", 2, "8-10 per side", 60, "Stable core, avoid twisting", "Back Muscles", 3, 32, 64);
        addExercise(day5, "Bodyweight Assisted Pull Up", 2, "AMRAP", 60, "Use assistance as needed", "Back Muscles", 4, 30, 60);
        addExercise(day5, "Hand Plank", 2, "30-45s hold", 60, "Straight line, core engaged", "Six Pack", 5, 22, 44);
        addExercise(day5, "Glute Bridge", 2, "12-15", 60, "Squeeze glutes", "Leg & Butt", 6, 30, 60);

        // Day 6 & 7 - Rest
        createWorkoutDay(plan, 6, "Day 6", true, "Rest");
        createWorkoutDay(plan, 7, "Day 7", true, "Rest");

        // Considerations
        plan.setConsiderations(List.of(
                PlanConsideration.builder().considerationText("Balanced diet with enough calories and protein").plan(plan).build(),
                PlanConsideration.builder().considerationText("Progressive overload").plan(plan).build(),
                PlanConsideration.builder().considerationText("Listen to your body").plan(plan).build(),
                PlanConsideration.builder().considerationText("Exercise variety").plan(plan).build()
        ));

        workoutPlanRepository.save(plan);
    }
    private void createBMILevel3Plan() {
        WorkoutPlan plan = WorkoutPlan.builder()
                .bmiLevel(3)
                .trainingSplit("Upper/Lower")
                .goal("Increase strength & muscle, improve fitness")
                .trainingLevel(TrainingLevel.INTERMEDIATE)
                .colorGradient("from-purple-400 to-pink-500")
                .build();

        // Day 1 - Upper Body
        WorkoutDay day1 = createWorkoutDay(plan, 1, "Day 1", false, null);
        addExercise(day1, "Barbell Reverse Grip Bench Press", 2, "8-10", 75, "Start with empty bar, gradual increase", "Chest Muscles", 1, 35, 70);
        addExercise(day1, "Dumbbell Row Bilateral", 2, "8-10 per side", 75, "Stable core, flat back", "Back Muscles", 2, 32, 64);
        addExercise(day1, "Dumbbell Overhead Press", 2, "8-10", 75, "Control weight, gradual increase", "Arm & Shoulder", 3, 30, 60);
        addExercise(day1, "Bodyweight Assisted Pull Up", 2, "AMRAP", 75, "Decrease assistance/increase weight", "Back Muscles", 4, 30, 60);
        addExercise(day1, "Dumbbell Curl", 2, "10-12", 60, "Controlled movements", "Arm & Shoulder", 5, 26, 52);
        addExercise(day1, "Dumbbell Seated Overhead Tricep Extension", 2, "10-12", 60, "Elbows close to head", "Arm & Shoulder", 6, 27, 54);

        // Day 2 - Lower Body
        WorkoutDay day2 = createWorkoutDay(plan, 2, "Day 2", false, null);
        addExercise(day2, "Barbell Squat", 2, "8-10", 75, "Start with empty bar, gradual increase", "Leg & Butt", 1, 38, 76);
        addExercise(day2, "Dumbbell Romanian Deadlift", 2, "10-12", 75, "Hinge at hips, straight back", "Leg & Butt", 2, 36, 72);
        addExercise(day2, "Machine Leg Press", 2, "10-12", 60, "Control weight, don't lock knees", "Leg & Butt", 3, 34, 68);
        addExercise(day2, "Machine Leg Extension", 2, "12-15", 60, "Controlled, no momentum", "Quads", 4, 30, 60);
        addExercise(day2, "Machine Seated Leg Curl", 2, "12-15", 60, "Squeeze hamstrings", "Hamstrings", 5, 30, 60);
        addExercise(day2, "Machine Standing Calf Raises", 2, "15-20", 60, "", "Calves", 6, 28, 56);

        // Day 3 - Rest
        createWorkoutDay(plan, 3, "Day 3", true, "Rest or Active Recovery");

        // Day 4 - Upper Body (Light)
        WorkoutDay day4 = createWorkoutDay(plan, 4, "Day 4", false, null);
        addExercise(day4, "Dumbbell Bench Press", 2, "12-15", 60, "Muscle-mind connection", "Chest Muscles", 1, 30, 60);
        addExercise(day4, "Dumbbell Row Bilateral", 2, "12-15", 60, "Lighter than Day 1", "Back Muscles", 2, 30, 60);
        addExercise(day4, "Dumbbell Lateral Raise", 2, "12-15", 60, "Shoulder stability", "Arm & Shoulder", 3, 28, 56);
        addExercise(day4, "Cable Rope Face Pulls", 2, "15-20", 60, "Light weight, rear deltoids", "Arm & Shoulder", 4, 26, 52);
        addExercise(day4, "Dumbbell Curl", 2, "12-15", 60, "", "Arm & Shoulder", 5, 26, 52);
        addExercise(day4, "Dumbbell Overhead Tricep Extension", 2, "12-15", 60, "", "Arm & Shoulder", 6, 27, 54);

        // Day 5 - Lower Body (Light)
        WorkoutDay day5 = createWorkoutDay(plan, 5, "Day 5", false, null);
        addExercise(day5, "Walking Lunge", 2, "10-12 per leg", 60, "Balance and control", "Leg & Butt", 1, 35, 70);
        addExercise(day5, "Glute Bridge", 2, "15-20", 60, "Glute activation", "Leg & Butt", 2, 30, 60);
        addExercise(day5, "Machine Seated Leg Curl", 2, "15-20", 60, "Squeeze hamstrings", "Hamstrings", 3, 30, 60);
        addExercise(day5, "Machine Leg Extension", 2, "15-20", 60, "Controlled movements", "Quads", 4, 30, 60);
        addExercise(day5, "Machine Standing Calf Raises", 2, "20-25", 60, "", "Calves", 5, 28, 56);

        // Day 6 & 7 - Rest
        createWorkoutDay(plan, 6, "Day 6", true, "Rest");
        createWorkoutDay(plan, 7, "Day 7", true, "Rest");

        // Add considerations
        plan.setConsiderations(List.of(
                PlanConsideration.builder().considerationText("Nutrition and recovery").plan(plan).build(),
                PlanConsideration.builder().considerationText("Progressive overload").plan(plan).build(),
                PlanConsideration.builder().considerationText("Listen to your body").plan(plan).build(),
                PlanConsideration.builder().considerationText("Exercise variety").plan(plan).build()
        ));

        workoutPlanRepository.save(plan);
    }
    private void createBMILevel4Plan() {
        WorkoutPlan plan = WorkoutPlan.builder()
                .bmiLevel(4)
                .trainingSplit("Upper/Lower")
                .goal("Balanced fitness (strength, hypertrophy, endurance)")
                .trainingLevel(TrainingLevel.INTERMEDIATE)
                .colorGradient("from-orange-400 to-red-500")
                .build();

        // Day 1 - Upper Body
        WorkoutDay day1 = createWorkoutDay(plan, 1, "Day 1", false, null);
        addExercise(day1, "Barbell Reverse Grip Bench Press", 3, "8-12", 75, "Controlled movements, full ROM", "Chest Muscles", 1, 35, 105);
        addExercise(day1, "Dumbbell Row Bilateral", 3, "8-12", 75, "Stable core", "Back Muscles", 2, 32, 96);
        addExercise(day1, "Dumbbell Overhead Press", 3, "8-12", 75, "Control weight", "Shoulders", 3, 30, 90);
        addExercise(day1, "Chin Ups", 3, "AMRAP", 75, "Or Lat Pulldowns (918)", "Back Muscles", 4, 34, 102);
        addExercise(day1, "Dumbbell Curl", 3, "10-15", 60, "Control weight", "Biceps", 5, 26, 78);
        addExercise(day1, "Cable Rope Pushdown", 3, "12-15", 60, "Elbows close to body", "Triceps", 6, 27, 81);

        // Day 2 - Lower Body
        WorkoutDay day2 = createWorkoutDay(plan, 2, "Day 2", false, null);
        addExercise(day2, "Barbell Squat", 3, "8-12", 105, "Proper form, deep as comfortable", "Leg & Butt", 1, 38, 114);
        addExercise(day2, "Dumbbell Romanian Deadlift", 3, "10-15", 105, "Hinge at hips, straight back", "Leg & Butt", 2, 36, 108);
        addExercise(day2, "Machine Leg Press", 3, "10-15", 90, "Control weight", "Leg & Butt", 3, 34, 102);
        addExercise(day2, "Machine Leg Extension", 3, "12-15", 60, "Control movement", "Quads", 4, 30, 90);
        addExercise(day2, "Machine Seated Leg Curl", 3, "12-15", 60, "Squeeze hamstrings", "Hamstrings", 5, 30, 90);
        addExercise(day2, "Dumbbell Calf Raise", 3, "15-20", 60, "Full ROM", "Calves", 6, 28, 84);

        // Day 3 - Rest
        createWorkoutDay(plan, 3, "Day 3", true, "Rest or Active Recovery (light cardio, stretching, yoga)");

        // Day 4 - Upper Body (Light)
        WorkoutDay day4 = createWorkoutDay(plan, 4, "Day 4", false, null);
        addExercise(day4, "Dumbbell Bench Press", 3, "12-15", 67, "Lighter than Day 1", "Chest Muscles", 1, 30, 90);
        addExercise(day4, "Dumbbell Row Bilateral", 3, "12-15", 67, "Lighter than Day 1", "Back Muscles", 2, 30, 90);
        addExercise(day4, "Dumbbell Arnold Press", 3, "10-15", 67, "Controlled movements", "Shoulders", 3, 30, 90);
        addExercise(day4, "Cable Rope Face Pulls", 3, "15-20", 45, "Light weight, rear deltoids", "Rear Shoulders", 4, 26, 78);
        addExercise(day4, "Dumbbell Curl", 2, "15-20", 45, "Lighter than Day 1", "Biceps", 5, 26, 52);
        addExercise(day4, "Dumbbell Overhead Tricep Extension", 2, "15-20", 45, "Lighter than Day 1", "Triceps", 6, 27, 54);

        // Day 5 - Lower Body (Light)
        WorkoutDay day5 = createWorkoutDay(plan, 5, "Day 5", false, null);
        addExercise(day5, "Walking Lunge", 3, "10-12 per leg", 67, "Balance and control", "Leg & Butt", 1, 35, 105);
        addExercise(day5, "Glute Bridge", 3, "15-20", 67, "Squeeze glutes", "Leg & Butt", 2, 30, 90);
        addExercise(day5, "Machine Seated Leg Curl", 3, "15-20", 45, "Squeeze hamstrings", "Hamstrings", 3, 30, 90);
        addExercise(day5, "Machine Leg Extension", 3, "15-20", 45, "Controlled movements", "Quads", 4, 30, 90);
        addExercise(day5, "Calf Raises", 3, "20-25", 45, "Full ROM", "Calves", 5, 28, 84);

        // Day 6 & 7 - Rest
        createWorkoutDay(plan, 6, "Day 6", true, "Rest");
        createWorkoutDay(plan, 7, "Day 7", true, "Rest");

        // Add considerations
        plan.setConsiderations(List.of(
                PlanConsideration.builder().considerationText("Balanced diet").plan(plan).build(),
                PlanConsideration.builder().considerationText("Progressive overload").plan(plan).build(),
                PlanConsideration.builder().considerationText("Proper form").plan(plan).build(),
                PlanConsideration.builder().considerationText("Adequate rest").plan(plan).build()
        ));

        workoutPlanRepository.save(plan);
    }

    private void createBMILevel5Plan() {
        WorkoutPlan plan = WorkoutPlan.builder()
                .bmiLevel(5)
                .trainingSplit("Full Body with Cardio")
                .goal("Improve cardio, build muscle, burn calories")
                .trainingLevel(TrainingLevel.INTERMEDIATE)
                .colorGradient("from-red-400 to-orange-600")
                .build();

        // Day 1 - Full Body + Cardio
        WorkoutDay day1 = createWorkoutDay(plan, 1, "Day 1", false, null);
        addExercise(day1, "Dumbbell Goblet Squat", 3, "10-12", 75, "Or Barbell Squats (572)", "Leg & Butt", 1, 38, 114);
        addExercise(day1, "Dumbbell Bench Press", 3, "10-12", 75, "Focus on chest activation", "Chest Muscles", 2, 35, 105);
        addExercise(day1, "Dumbbell Row Bilateral", 3, "10-12 per side", 75, "Keep core engaged", "Back Muscles", 3, 34, 102);
        addExercise(day1, "Dumbbell Overhead Press", 3, "10-12", 75, "Dumbbells or barbells", "Shoulders", 4, 30, 90);
        addExercise(day1, "Hand Plank", 3, "30-60s hold", 75, "Engage core", "Core", 5, 24, 72);
        addExercise(day1, "Treadmill Walk", 1, "30-45 minutes", 0, "Or Cycling, Elliptical, etc.", "Cardio", 6, 0, 0);

        // Day 2 - Rest
        createWorkoutDay(plan, 2, "Day 2", true, "Rest or Light Activity (walking, stretching)");

        // Day 3 - Full Body + Cardio
        WorkoutDay day3 = createWorkoutDay(plan, 3, "Day 3", false, null);
        addExercise(day3, "Dumbbell Romanian Deadlift", 3, "10-12", 75, "Hinge at hips, straight back", "Hamstrings & Glutes", 1, 36, 108);
        addExercise(day3, "Dumbbell Reverse Lunge", 3, "10-12 per leg", 75, "Maintain balance", "Leg & Butt", 2, 36, 108);
        addExercise(day3, "Dumbbell Curl", 3, "12-15", 60, "Controlled movement", "Biceps", 3, 26, 78);
        addExercise(day3, "Dumbbell Overhead Tricep Extension", 3, "12-15", 60, "Elbows close to head", "Triceps", 4, 27, 81);
        addExercise(day3, "Bicycle Crunch", 3, "15-20 per side", 60, "Engage core", "Six Pack", 5, 28, 84);
        addExercise(day3, "Cycling or Rowing", 1, "30-45 minutes", 0, "Choose different from Day 1", "Cardio", 6, 0, 0);

        // Day 4 - Rest
        createWorkoutDay(plan, 4, "Day 4", true, "Rest or Light Activity");

        // Day 5 - Circuit + Cardio
        WorkoutDay day5 = createWorkoutDay(plan, 5, "Day 5", false, null);
        addExercise(day5, "Bodyweight Squat", 2, "15", 30, "", "Leg & Butt", 1, 32, 64);
        addExercise(day5, "Push Up", 2, "AMRAP", 30, "Or Incline Push-Ups", "Chest & Triceps", 2, 28, 56);
        addExercise(day5, "Dumbbell Row Bilateral", 2, "10-12 per side", 30, "", "Back Muscles", 3, 32, 64);
        addExercise(day5, "Cardio Jumping Jacks", 2, "20", 30, "", "Full Body", 4, 30, 60);
        addExercise(day5, "Mountain Climber", 2, "15-20 per side", 30, "", "Core & Cardio", 5, 28, 56);
        addExercise(day5, "Elliptical or Outdoor Jog", 1, "30-45 minutes", 0, "Choose different from Day 1 & 3", "Cardio", 6, 0, 0);

        // Day 6 & 7 - Rest
        createWorkoutDay(plan, 6, "Day 6", true, "Rest");
        createWorkoutDay(plan, 7, "Day 7", true, "Rest");

        // Add considerations
        plan.setConsiderations(List.of(
                PlanConsideration.builder().considerationText("Calorie-controlled diet").plan(plan).build(),
                PlanConsideration.builder().considerationText("Gradual progression").plan(plan).build(),
                PlanConsideration.builder().considerationText("Listen to your body").plan(plan).build(),
                PlanConsideration.builder().considerationText("Stay hydrated").plan(plan).build(),
                PlanConsideration.builder().considerationText("Find enjoyable activities").plan(plan).build()
        ));

        workoutPlanRepository.save(plan);
    }
    private void createBMILevel6Plan() {
        WorkoutPlan plan = WorkoutPlan.builder()
                .bmiLevel(6)
                .trainingSplit("Full Body with Cardio")
                .goal("Improve cardio, strength, mobility; build foundation")
                .trainingLevel(TrainingLevel.BEGINNER)
                .colorGradient("from-orange-600 to-red-700")
                .build();

        // Day 1
        WorkoutDay day1 = createWorkoutDay(plan, 1, "Day 1", false, null);
        addExercise(day1, "Chair Pose", 2, "8-10", 60, "Controlled movements", "Legs & Core", 1, 18, 36);
        addExercise(day1, "Incline Push Up", 2, "AMRAP", 60, "Or Wall Push-Ups", "Chest & Arms", 2, 20, 40);
        addExercise(day1, "Adductor Stretch Seated Bilateral Static", 2, "10-12", 60, "Resistance band or light dumbbells", "Inner Thighs", 3, 12, 24);
        addExercise(day1, "Bird Dog", 2, "8-10 per side", 60, "Engage core, balance", "Core & Back", 4, 16, 32);
        addExercise(day1, "Hand Plank", 2, "20-30s hold", 60, "From knees if needed", "Core", 5, 14, 28);
        addExercise(day1, "Treadmill Walk", 1, "20-30 minutes", 0, "Or Water Aerobics, Stationary Bike (light resistance)", "Cardio", 6, 0, 0);

        // Day 2
        createWorkoutDay(plan, 2, "Day 2", true, "Rest or Light Activity (gentle stretching, short walk)");

        // Day 3
        WorkoutDay day3 = createWorkoutDay(plan, 3, "Day 3", false, null);
        addExercise(day3, "Chair Pose", 2, "8-10", 60, "", "Legs & Core", 1, 18, 36);
        addExercise(day3, "Incline Push Up", 2, "AMRAP", 60, "Or Wall Push-Ups", "Chest & Arms", 2, 20, 40);
        addExercise(day3, "Band Pull Apart", 2, "15-20", 60, "Squeeze shoulder blades", "Upper Back & Shoulders", 3, 15, 30);
        addExercise(day3, "Bird Dog", 2, "8-10 per side", 60, "", "Core & Back", 4, 16, 32);
        addExercise(day3, "Elbow Side Plank", 2, "20-30s hold per side", 60, "From knees if needed", "Obliques & Core", 5, 14, 28);
        addExercise(day3, "Stationary Bike or Water Walking", 1, "20-30 minutes", 0, "Different from Day 1", "Cardio", 6, 0, 0);

        // Day 4
        createWorkoutDay(plan, 4, "Day 4", true, "Rest or Light Activity");

        // Day 5
        WorkoutDay day5 = createWorkoutDay(plan, 5, "Day 5", false, null);
        addExercise(day5, "Chair Pose", 2, "8-10", 60, "Controlled movements", "Legs & Core", 1, 18, 36);
        addExercise(day5, "Incline Push Up", 2, "AMRAP", 60, "Or Wall Push-Ups", "Chest & Arms", 2, 20, 40);
        addExercise(day5, "Adductor Stretch Seated Bilateral Static", 2, "10-12", 60, "Resistance band or light dumbbells", "Inner Thighs", 3, 12, 24);
        addExercise(day5, "Bird Dog", 2, "8-10 per side", 60, "Engage core, balance", "Core & Back", 4, 16, 32);
        addExercise(day5, "Hand Plank", 2, "20-30s hold", 60, "From knees if needed", "Core", 5, 14, 28);
        addExercise(day5, "Elliptical or Light Swim", 1, "30-40 minutes (if tolerated)", 0, "Different from Day 1 & 3", "Cardio", 6, 0, 0);

        // Day 6 & 7
        createWorkoutDay(plan, 6, "Day 6", true, "Rest");
        createWorkoutDay(plan, 7, "Day 7", true, "Rest");

        // Add considerations
        plan.setConsiderations(List.of(
                PlanConsideration.builder().considerationText("Medical clearance essential").plan(plan).build(),
                PlanConsideration.builder().considerationText("Gradual progression").plan(plan).build(),
                PlanConsideration.builder().considerationText("Healthy diet").plan(plan).build(),
                PlanConsideration.builder().considerationText("Modifications as needed").plan(plan).build(),
                PlanConsideration.builder().considerationText("Consistency is key").plan(plan).build()
        ));

        workoutPlanRepository.save(plan);
    }
    private void createBMILevel7Plan() {
        WorkoutPlan plan = WorkoutPlan.builder()
                .bmiLevel(7)
                .trainingSplit("Full Body")
                .goal("Improve mobility, increase activity, enhance cardio")
                .trainingLevel(TrainingLevel.BEGINNER)
                .colorGradient("from-red-700 to-black")
                .build();

        // Day 1
        WorkoutDay day1 = createWorkoutDay(plan, 1, "Day 1", false, null);
        addExercise(day1, "Cardio Quick Feet", 2, "10-15", 90, "Seated Marches (in chair, light effort, mimicking marching motion); Using Quick Feet as a rhythmic seated movement", "Cardio & Legs", 1, 18, 36);
        addExercise(day1, "Push Up", 2, "AMRAP", 90, "Wall Push-Ups (or Incline Push-Ups if easier)", "Chest & Arms", 2, 20, 40);
        addExercise(day1, "Chair Pose", 2, "5-8", 90, "Chair Stands (use arms for assistance)", "Legs & Glutes", 3, 16, 32);
        addExercise(day1, "Backward Arm Circle", 2, "8-10", 60, "Overhead Arm Raises (seated or standing, light/no weight)", "Shoulders & Arms", 4, 12, 24);
        addExercise(day1, "Dumbbell Seated Calf Raise", 1, "10-12", 60, "Seated Bicep Curls (light weights or resistance band)", "Calves", 5, 10, 10);
        addExercise(day1, "Dumbbell Overhead Tricep Extension", 1, "10-12", 60, "Overhead Tricep Extensions (light weight or resistance band, focus on triceps)", "Triceps", 6, 10, 10);
        addExercise(day1, "Water Aerobics", 1, "20-30 minutes", 0, "Highly recommended", "Cardio", 7, 0, 0);
        addExercise(day1, "Treadmill Walk", 1, "10-15 min (if no Water Aerobics)", 0, "", "Cardio", 8, 0, 0);

        // Day 2
        createWorkoutDay(plan, 2, "Day 2", true, "Rest");

        // Day 3
        WorkoutDay day3 = createWorkoutDay(plan, 3, "Day 3", false, null);
        addExercise(day3, "Seated Forward Bend", 2, "30-60s hold", 60, "Seated Forward Bend (modify by bending knees)", "Lower Back & Hamstrings", 1, 12, 24);
        addExercise(day3, "Seated Ulnar Nerve Slider", 2, "10-12", 60, "Seated Ulnar Nerve Slider (Adapt this arm movement along the wall as a seated wall slide)", "Arms & Shoulders", 2, 10, 20);
        addExercise(day3, "Bird Dog", 2, "5-8 per side", 60, "Bird Dog (modify on knees)", "Core & Lower Back", 3, 14, 28);
        addExercise(day3, "Dumbbell Standing Hip Abduction", 2, "10-12 per side", 60, "Standing Hip Abduction (use chair for balance)", "Glutes & Hips", 4, 16, 32);
        addExercise(day3, "Treadmill Walk", 1, "10-15 minutes", 0, "Walking (gentle pace, flat surface, intervals if needed)", "Cardio", 5, 30, 30);
        addExercise(day3, "Water Aerobics", 1, "20-30 minutes", 0, "Highly recommended, low impact", "Cardio", 6, 0, 0);
        addExercise(day3, "Stationary Bike", 1, "10-15 min (if no Water Aerobics)", 0, "Light Resistance", "Cardio", 7, 0, 0);

        // Day 4
        createWorkoutDay(plan, 4, "Day 4", true, "Rest");

        // Day 5
        WorkoutDay day5 = createWorkoutDay(plan, 5, "Day 5", false, null);
        addExercise(day5, "Cardio Quick Feet", 2, "10-15", 90, "Seated Marches (in chair, light effort, mimicking marching motion); Using Quick Feet as a rhythmic seated movement", "Cardio & Legs", 1, 18, 36);
        addExercise(day5, "Push Up", 2, "AMRAP", 90, "Wall Push-Ups (or Incline Push-Ups if easier)", "Chest & Arms", 2, 20, 40);
        addExercise(day5, "Chair Pose", 2, "5-8", 90, "Chair Stands (use arms for assistance)", "Legs & Glutes", 3, 16, 32);
        addExercise(day5, "Backward Arm Circle", 2, "8-10", 60, "Overhead Arm Raises (seated or standing, light/no weight)", "Shoulders & Arms", 4, 12, 24);
        addExercise(day5, "Dumbbell Seated Calf Raise", 1, "10-12", 60, "Seated Bicep Curls (light weights or resistance band)", "Calves", 5, 10, 10);
        addExercise(day5, "Dumbbell Overhead Tricep Extension", 1, "10-12", 60, "Overhead Tricep Extensions (light weight or resistance band, focus on triceps)", "Triceps", 6, 10, 10);
        addExercise(day5, "Water Aerobics", 1, "20-30 minutes", 0, "Highly recommended", "Cardio", 7, 0, 0);
        addExercise(day5, "Treadmill Walk", 1, "10-15 min (if no Water Aerobics)", 0, "", "Cardio", 8, 0, 0);

        // Day 6 & 7
        createWorkoutDay(plan, 6, "Day 6", true, "Rest");
        createWorkoutDay(plan, 7, "Day 7", true, "Rest");

        // Considerations
        plan.setConsiderations(List.of(
                PlanConsideration.builder().considerationText("Medical supervision highly recommended").plan(plan).build(),
                PlanConsideration.builder().considerationText("Start slowly, prioritize comfort").plan(plan).build(),
                PlanConsideration.builder().considerationText("Gradual progression").plan(plan).build(),
                PlanConsideration.builder().considerationText("Healthy diet").plan(plan).build(),
                PlanConsideration.builder().considerationText("Listen to your body").plan(plan).build()
        ));

        workoutPlanRepository.save(plan);
    }
    private void createBMILevel8Plan() {
        WorkoutPlan plan = WorkoutPlan.builder()
                .bmiLevel(8)
                .trainingSplit("Push/Pull/Legs x2 + Core Focus")
                .goal("Build muscle, improve strength, maintain low body fat")
                .trainingLevel(TrainingLevel.ADVANCED)
                .colorGradient("from-black to-gray-900")
                .build();

        // Day 1 - Push
        WorkoutDay day1 = createWorkoutDay(plan, 1, "Day 1", false, null);
        addExercise(day1, "Barbell Bench Press", 4, "6-8", 120, "Focus on power, full range", "Chest Muscles", 1, 40, 160);
        addExercise(day1, "Incline Dumbbell Press", 3, "8-10", 90, "Controlled tempo", "Chest Muscles", 2, 35, 105);
        addExercise(day1, "Dumbbell Lateral Raises", 3, "12-15", 60, "Pause at the top", "Arm & Shoulder", 3, 28, 84);
        addExercise(day1, "Dips", 3, "AMRAP", 60, "Add weight if possible", "Chest Muscles", 4, 34, 102);
        addExercise(day1, "Cable Rope Pushdown", 3, "12-15", 60, "Strict form", "Arm & Shoulder", 5, 28, 84);

        // Day 2 - Pull
        WorkoutDay day2 = createWorkoutDay(plan, 2, "Day 2", false, null);
        addExercise(day2, "Barbell Deadlift", 4, "5-6", 120, "Explosive lift, heavy weight", "Back Muscles", 1, 45, 180);
        addExercise(day2, "Weighted Pull-Ups", 3, "AMRAP", 90, "Control the eccentric", "Back Muscles", 2, 36, 108);
        addExercise(day2, "Barbell Row", 3, "8-10", 90, "Flat back, full ROM", "Back Muscles", 3, 32, 96);
        addExercise(day2, "Incline Curl", 3, "10-12", 60, "Stretch fully", "Arm & Shoulder", 4, 26, 78);
        addExercise(day2, "Hammer Curl", 3, "10-12", 60, "Keep elbows tight", "Arm & Shoulder", 5, 26, 78);

        // Day 3 - Legs
        WorkoutDay day3 = createWorkoutDay(plan, 3, "Day 3", false, null);
        addExercise(day3, "Barbell Squat", 4, "6-8", 120, "Deep and powerful reps", "Leg & Butt", 1, 44, 176);
        addExercise(day3, "Romanian Deadlift", 3, "10-12", 90, "Hamstring stretch focus", "Leg & Butt", 2, 38, 114);
        addExercise(day3, "Walking Lunges", 3, "12 per leg", 90, "Dumbbells optional", "Leg & Butt", 3, 36, 108);
        addExercise(day3, "Leg Extension", 3, "12-15", 60, "Pause at top", "Leg & Butt", 4, 30, 90);
        addExercise(day3, "Calf Raise", 3, "20-25", 60, "Slow tempo", "Leg & Butt", 5, 28, 84);

        // Day 4 - Recovery
        createWorkoutDay(plan, 4, "Day 4", true, "Rest or Active Recovery (Light Cardio, Mobility, Foam Rolling)");

        // Day 5 - Push Variation
        WorkoutDay day5 = createWorkoutDay(plan, 5, "Day 5", false, null);
        addExercise(day5, "Dumbbell Shoulder Press", 4, "8-10", 90, "Strict form", "Arm & Shoulder", 1, 32, 128);
        addExercise(day5, "Incline Dumbbell Fly", 3, "10-12", 60, "Stretch chest", "Chest Muscles", 2, 30, 90);
        addExercise(day5, "Push Ups", 3, "AMRAP", 60, "Final burnout", "Chest Muscles", 3, 28, 84);
        addExercise(day5, "Overhead Cable Tricep Extension", 3, "12-15", 60, "Stretch at bottom", "Arm & Shoulder", 4, 27, 81);

        // Day 6 - Pull Variation
        WorkoutDay day6 = createWorkoutDay(plan, 6, "Day 6", false, null);
        addExercise(day6, "Lat Pulldown", 4, "10-12", 90, "Full stretch at top", "Back Muscles", 1, 34, 136);
        addExercise(day6, "Dumbbell Row Bilateral", 3, "10-12", 90, "Keep core tight", "Back Muscles", 2, 32, 96);
        addExercise(day6, "Face Pulls", 3, "15-20", 60, "Focus on rear delts", "Arm & Shoulder", 3, 28, 84);
        addExercise(day6, "EZ Bar Curl", 3, "10-12", 60, "Squeeze at top", "Arm & Shoulder", 4, 26, 78);

        // Day 7 - Core Focus
        WorkoutDay day7 = createWorkoutDay(plan, 7, "Day 7", false, null);
        addExercise(day7, "Hanging Leg Raise", 3, "15-20", 60, "Strict form, no swinging", "Six Pack", 1, 26, 78);
        addExercise(day7, "Russian Twist with Weight", 3, "20-30", 60, "Each side counts", "Six Pack", 2, 28, 84);
        addExercise(day7, "Plank with Reach", 3, "30s hold + reach", 60, "Engage entire core", "Six Pack", 3, 24, 72);
        addExercise(day7, "Mountain Climbers", 3, "20 per leg", 45, "Cardio + Core finisher", "Six Pack", 4, 30, 90);

        // Considerations
        plan.setConsiderations(List.of(
                PlanConsideration.builder().considerationText("Warm-up before each session").plan(plan).build(),
                PlanConsideration.builder().considerationText("Cool-down stretches after workout").plan(plan).build(),
                PlanConsideration.builder().considerationText("Focus on progressive overload").plan(plan).build(),
                PlanConsideration.builder().considerationText("Prioritize form over weight").plan(plan).build(),
                PlanConsideration.builder().considerationText("Fuel your body with proper nutrition and hydration").plan(plan).build()
        ));

        workoutPlanRepository.save(plan);
    }



    private WorkoutDay createWorkoutDay(WorkoutPlan plan, int dayNumber, String dayName, boolean isRest, String restNotes) {
        WorkoutDay day = WorkoutDay.builder()
            .workoutPlan(plan)
            .dayNumber(dayNumber)
            .dayName(dayName)
            .isRestDay(isRest)
            .restDayNotes(restNotes)
            .build();
        // Optionally, add the day to the plan's list if needed
         plan.getWorkoutDays().add(day);
        log.info("Created WorkoutDay: {} for WorkoutPlan: {}", day, plan.getId());
        return day;
    }

    private void addExercise(
            WorkoutDay day,
            String name,
            int sets,
            String reps,
            int rest,
            String notes,
            String bodyFocus,
            int order  ,
            Integer caloriesBurned,
            Integer totalCalories
    ) {
        Exercise exercise = Exercise.builder()
                .workoutDay(day)
                .exerciseName(name)
                .setsCount(sets)
                .reps(reps)
                .restSeconds(rest)
                .notes(notes)
                .bodyFocus(bodyFocus)
                .caloriesBurned(caloriesBurned)
                .totalCalories(totalCalories)
                .imageUrl(null)         // مبدئيًا null
                .videoUrl(null)         // مبدئيًا null
                .isCompleted(false)
                .exerciseOrder(order)
                .build();

        day.getExercises().add(exercise);
    }



}