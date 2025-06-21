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
            log.info("CommandLineRunnerBean initialized successfully");

            if (workoutPlanRepository.count() == 0) {
                insertWorkoutPlans();
                log.info("Workout plans initialized successfully");
            } else {
                log.info("Workout plans already exist, skipping initialization");
            }
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
        addExercise(day1, "Bodyweight Squat", 2, "8-10", 60, "Proper form, deep as comfortable", 1);
        addExercise(day1, "Incline Push Up", 2, "AMRAP", 60, "Hands elevated, modify if needed", 2);
        addExercise(day1, "Bodyweight Assisted Pull Up", 2, "AMRAP", 60, "Use assistance, decrease over time", 3);
        addExercise(day1, "Hand Plank", 2, "20-30s hold", 60, "Straight line head to heels", 4);
        addExercise(day1, "Bird Dog", 2, "10-12 per side", 60, "Engage core, balance", 5);
        addExercise(day1, "Glute Bridge", 2, "12-15", 60, "Squeeze glutes", 6);

        // Day 2 - Rest
        createWorkoutDay(plan, 2, "Day 2", true, "Rest or Light Activity");

        // Day 3
        WorkoutDay day3 = createWorkoutDay(plan, 3, "Day 3", false, null);
        addExercise(day3, "Bodyweight Squat", 2, "8-10", 60, "Controlled movements", 1);
        addExercise(day3, "Incline Push Up", 2, "AMRAP", 60, "", 2);
        addExercise(day3, "Inverted Row", 2, "AMRAP", 60, "Sturdy table or TRX, lower slowly", 3);
        addExercise(day3, "Elbow Side Plank", 2, "20-30s hold per side", 60, "Straight line", 4);
        addExercise(day3, "Supermans", 2, "10-12", 60, "Glutes and lower back", 5);
        addExercise(day3, "Laying Leg Raises", 2, "10-12", 60, "Lower back on ground", 6);

        // Day 4 - Rest
        createWorkoutDay(plan, 4, "Day 4", true, "Rest or Light Activity");

        // Day 5 - Same as Day 1
        WorkoutDay day5 = createWorkoutDay(plan, 5, "Day 5", false, null);
        addExercise(day5, "Bodyweight Squat", 2, "8-10", 60, "Proper form, deep as comfortable", 1);
        addExercise(day5, "Incline Push Up", 2, "AMRAP", 60, "Hands elevated, modify if needed", 2);
        addExercise(day5, "Bodyweight Assisted Pull Up", 2, "AMRAP", 60, "Use assistance, decrease over time", 3);
        addExercise(day5, "Hand Plank", 2, "20-30s hold", 60, "Straight line head to heels", 4);
        addExercise(day5, "Bird Dog", 2, "10-12 per side", 60, "Engage core, balance", 5);
        addExercise(day5, "Glute Bridge", 2, "12-15", 60, "Squeeze glutes", 6);

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
        addExercise(day1, "Dumbbell Goblet Squat", 2, "8-10", 60, "Use appropriate weight", 1);
        addExercise(day1, "Dumbbell Bench Press", 2, "8-10", 60, "Chest activation", 2);
        addExercise(day1, "Dumbbell Row Unilateral", 2, "8-10 per side", 60, "Stable core, avoid twisting", 3);
        addExercise(day1, "Bodyweight Assisted Pull Up", 2, "AMRAP", 60, "Use assistance as needed", 4);
        addExercise(day1, "Hand Plank", 2, "30-45s hold", 60, "Straight line, core engaged", 5);
        addExercise(day1, "Glute Bridge", 2, "12-15", 60, "Squeeze glutes", 6);

        // Day 2 - Rest
        createWorkoutDay(plan, 2, "Day 2", true, "Rest or Light Activity (yoga, walking, stretching)");

        // Day 3
        WorkoutDay day3 = createWorkoutDay(plan, 3, "Day 3", false, null);
        addExercise(day3, "Dumbbell Romanian Deadlift", 2, "10-12", 60, "Hinge at hips, straight back", 1);
        addExercise(day3, "Dumbbell Overhead Press", 2, "8-10", 60, "Control weight", 2);
        addExercise(day3, "Inverted Row", 2, "AMRAP", 60, "Choose appropriate variation", 3);
        addExercise(day3, "Elbow Side Plank", 2, "30-45s hold per side", 60, "Straight line, core engaged", 4);
        addExercise(day3, "Bird Dog", 2, "10-12 per side", 60, "Engage core, balance", 5);
        addExercise(day3, "Dumbbell Curl", 2, "10-12", 60, "Controlled movements", 6);

        // Day 4 - Rest
        createWorkoutDay(plan, 4, "Day 4", true, "Rest or Light Activity");

        // Day 5 - Same as Day 1
        WorkoutDay day5 = createWorkoutDay(plan, 5, "Day 5", false, null);
        addExercise(day5, "Dumbbell Goblet Squat", 2, "8-10", 60, "Use appropriate weight", 1);
        addExercise(day5, "Dumbbell Bench Press", 2, "8-10", 60, "Chest activation", 2);
        addExercise(day5, "Dumbbell Row Unilateral", 2, "8-10 per side", 60, "Stable core, avoid twisting", 3);
        addExercise(day5, "Bodyweight Assisted Pull Up", 2, "AMRAP", 60, "Use assistance as needed", 4);
        addExercise(day5, "Hand Plank", 2, "30-45s hold", 60, "Straight line, core engaged", 5);
        addExercise(day5, "Glute Bridge", 2, "12-15", 60, "Squeeze glutes", 6);

        // Day 6 & 7 - Rest
        createWorkoutDay(plan, 6, "Day 6", true, "Rest");
        createWorkoutDay(plan, 7, "Day 7", true, "Rest");

        // Add considerations
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
        addExercise(day1, "Barbell Reverse Grip Bench Press", 2, "8-10", 75, "Start with empty bar, gradual increase", 1);
        addExercise(day1, "Dumbbell Row Bilateral", 2, "8-10 per side", 75, "Stable core, flat back", 2);
        addExercise(day1, "Dumbbell Overhead Press", 2, "8-10", 75, "Control weight, gradual increase", 3);
        addExercise(day1, "Bodyweight Assisted Pull Up", 2, "AMRAP", 75, "Decrease assistance/increase weight", 4);
        addExercise(day1, "Dumbbell Curl", 2, "10-12", 60, "Controlled movements", 5);
        addExercise(day1, "Dumbbell Seated Overhead Tricep Extension", 2, "10-12", 60, "Elbows close to head", 6);

        // Day 2 - Lower Body
        WorkoutDay day2 = createWorkoutDay(plan, 2, "Day 2", false, null);
        addExercise(day2, "Barbell Squat", 2, "8-10", 75, "Start with empty bar, gradual increase", 1);
        addExercise(day2, "Dumbbell Romanian Deadlift", 2, "10-12", 75, "Hinge at hips, straight back", 2);
        addExercise(day2, "Machine Leg Press", 2, "10-12", 60, "Control weight, don't lock knees", 3);
        addExercise(day2, "Machine Leg Extension", 2, "12-15", 60, "Controlled, no momentum", 4);
        addExercise(day2, "Machine Seated Leg Curl", 2, "12-15", 60, "Squeeze hamstrings", 5);
        addExercise(day2, "Machine Standing Calf Raises", 2, "15-20", 60, "", 6);

        // Day 3 - Rest
        createWorkoutDay(plan, 3, "Day 3", true, "Rest or Active Recovery");

        // Day 4 - Upper Body (Light)
        WorkoutDay day4 = createWorkoutDay(plan, 4, "Day 4", false, null);
        addExercise(day4, "Dumbbell Bench Press", 2, "12-15", 60, "Muscle-mind connection", 1);
        addExercise(day4, "Dumbbell Row Bilateral", 2, "12-15", 60, "Lighter than Day 1", 2);
        addExercise(day4, "Dumbbell Lateral Raise", 2, "12-15", 60, "Shoulder stability", 3);
        addExercise(day4, "Cable Rope Face Pulls", 2, "15-20", 60, "Light weight, rear deltoids", 4);
        addExercise(day4, "Dumbbell Curl", 2, "12-15", 60, "", 5);
        addExercise(day4, "Dumbbell Overhead Tricep Extension", 2, "12-15", 60, "", 6);

        // Day 5 - Lower Body (Light)
        WorkoutDay day5 = createWorkoutDay(plan, 5, "Day 5", false, null);
        addExercise(day5, "Walking Lunge", 2, "10-12 per leg", 60, "Balance and control", 1);
        addExercise(day5, "Glute Bridge", 2, "15-20", 60, "Glute activation", 2);
        addExercise(day5, "Machine Seated Leg Curl", 2, "15-20", 60, "Squeeze hamstrings", 3);
        addExercise(day5, "Machine Leg Extension", 2, "15-20", 60, "Controlled movements", 4);
        addExercise(day5, "Machine Standing Calf Raises", 2, "20-25", 60, "", 5);

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
        addExercise(day1, "Barbell Reverse Grip Bench Press", 3, "8-12", 75, "Controlled movements, full ROM", 1);
        addExercise(day1, "Dumbbell Row Bilateral", 3, "8-12", 75, "Stable core", 2);
        addExercise(day1, "Dumbbell Overhead Press", 3, "8-12", 75, "Control weight", 3);
        addExercise(day1, "Chin Ups", 3, "AMRAP", 75, "Or Lat Pulldowns", 4);
        addExercise(day1, "Dumbbell Curl", 3, "10-15", 60, "Control weight", 5);
        addExercise(day1, "Cable Rope Pushdown", 3, "12-15", 60, "Elbows close to body", 6);

        // Day 2 - Lower Body
        WorkoutDay day2 = createWorkoutDay(plan, 2, "Day 2", false, null);
        addExercise(day2, "Barbell Squat", 3, "8-12", 105, "Proper form, deep as comfortable", 1);
        addExercise(day2, "Dumbbell Romanian Deadlift", 3, "10-15", 105, "Hinge at hips, straight back", 2);
        addExercise(day2, "Machine Leg Press", 3, "10-15", 90, "Control weight", 3);
        addExercise(day2, "Machine Leg Extension", 3, "12-15", 60, "Control movement", 4);
        addExercise(day2, "Machine Seated Leg Curl", 3, "12-15", 60, "Squeeze hamstrings", 5);
        addExercise(day2, "Dumbbell Calf Raise", 3, "15-20", 60, "Full ROM", 6);

        // Day 3 - Rest
        createWorkoutDay(plan, 3, "Day 3", true, "Rest or Active Recovery (light cardio, stretching, yoga)");

        // Day 4 - Upper Body (Light)
        WorkoutDay day4 = createWorkoutDay(plan, 4, "Day 4", false, null);
        addExercise(day4, "Dumbbell Bench Press", 3, "12-15", 67, "Lighter than Day 1", 1);
        addExercise(day4, "Dumbbell Row Bilateral", 3, "12-15", 67, "Lighter than Day 1", 2);
        addExercise(day4, "Dumbbell Arnold Press", 3, "10-15", 67, "Controlled movements", 3);
        addExercise(day4, "Cable Rope Face Pulls", 3, "15-20", 45, "Light weight, rear deltoids", 4);
        addExercise(day4, "Dumbbell Curl", 2, "15-20", 45, "Lighter than Day 1", 5);
        addExercise(day4, "Dumbbell Overhead Tricep Extension", 2, "15-20", 45, "Lighter than Day 1", 6);

        // Day 5 - Lower Body (Light)
        WorkoutDay day5 = createWorkoutDay(plan, 5, "Day 5", false, null);
        addExercise(day5, "Walking Lunge", 3, "10-12 per leg", 67, "Balance and control", 1);
        addExercise(day5, "Glute Bridge", 3, "15-20", 67, "Squeeze glutes", 2);
        addExercise(day5, "Machine Seated Leg Curl", 3, "15-20", 45, "Squeeze hamstrings", 3);
        addExercise(day5, "Machine Leg Extension", 3, "15-20", 45, "Controlled movements", 4);
        addExercise(day5, "Calf Raises", 3, "20-25", 45, "Full ROM", 5);

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
        addExercise(day1, "Dumbbell Goblet Squat", 3, "10-12", 75, "Or Barbell Squats", 1);
        addExercise(day1, "Dumbbell Bench Press", 3, "10-12", 75, "Focus on chest activation", 2);
        addExercise(day1, "Dumbbell Row Bilateral", 3, "10-12 per side", 75, "Keep core engaged", 3);
        addExercise(day1, "Dumbbell Overhead Press", 3, "10-12", 75, "Dumbbells or barbells", 4);
        addExercise(day1, "Hand Plank", 3, "30-60s hold", 75, "Engage core", 5);
        addExercise(day1, "Treadmill Walk", 1, "30-45 minutes", 0, "Or Cycling, Elliptical, etc.", 6);

        // Day 2 - Rest
        createWorkoutDay(plan, 2, "Day 2", true, "Rest or Light Activity (walking, stretching)");

        // Day 3 - Full Body + Cardio
        WorkoutDay day3 = createWorkoutDay(plan, 3, "Day 3", false, null);
        addExercise(day3, "Dumbbell Romanian Deadlift", 3, "10-12", 75, "Hinge at hips, straight back", 1);
        addExercise(day3, "Dumbbell Reverse Lunge", 3, "10-12 per leg", 75, "Maintain balance", 2);
        addExercise(day3, "Dumbbell Curl", 3, "12-15", 60, "Controlled movement", 3);
        addExercise(day3, "Dumbbell Overhead Tricep Extension", 3, "12-15", 60, "Elbows close to head", 4);
        addExercise(day3, "Bicycle Crunch", 3, "15-20 per side", 60, "Engage core", 5);
        addExercise(day3, "Cardio Activity", 1, "30-45 minutes", 0, "Choose different from Day 1", 6);

        // Day 4 - Rest
        createWorkoutDay(plan, 4, "Day 4", true, "Rest or Light Activity");

        // Day 5 - Circuit + Cardio
        WorkoutDay day5 = createWorkoutDay(plan, 5, "Day 5", false, null);
        addExercise(day5, "Bodyweight Squat", 2, "15", 30, "", 1);
        addExercise(day5, "Push Up", 2, "AMRAP", 30, "Or Incline Push-Ups", 2);
        addExercise(day5, "Dumbbell Row Bilateral", 2, "10-12 per side", 30, "", 3);
        addExercise(day5, "Cardio Jumping Jacks", 2, "20", 30, "", 4);
        addExercise(day5, "Mountain Climber", 2, "15-20 per side", 30, "", 5);
        addExercise(day5, "Cardio Activity", 1, "30-45 minutes", 0, "Choose different from Day 1 & 3", 6);

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
        addExercise(day1, "Chair Pose", 2, "8-10", 60, "Controlled movements", 1);
        addExercise(day1, "Incline Push Up", 2, "AMRAP", 60, "Or Wall Push-Ups", 2);
        addExercise(day1, "Adductor Stretch Seated Bilateral Static", 2, "10-12", 60, "Resistance band or light dumbbells", 3);
        addExercise(day1, "Bird Dog", 2, "8-10 per side", 60, "Engage core, balance", 4);
        addExercise(day1, "Hand Plank", 2, "20-30s hold", 60, "From knees if needed", 5);
        addExercise(day1, "Treadmill Walk", 1, "20-30 minutes", 0, "Or Water Aerobics, Stationary Bike (light resistance)", 6);

        // Day 2
        createWorkoutDay(plan, 2, "Day 2", true, "Rest or Light Activity (gentle stretching, short walk)");

        // Day 3
        WorkoutDay day3 = createWorkoutDay(plan, 3, "Day 3", false, null);
        addExercise(day3, "Chair Pose", 2, "8-10", 60, "", 1);
        addExercise(day3, "Incline Push Up", 2, "AMRAP", 60, "Or Wall Push-Ups", 2);
        addExercise(day3, "Band Pull Apart", 2, "15-20", 60, "Squeeze shoulder blades", 3);
        addExercise(day3, "Bird Dog", 2, "8-10 per side", 60, "", 4);
        addExercise(day3, "Elbow Side Plank", 2, "20-30s hold per side", 60, "From knees if needed", 5);
        addExercise(day3, "Cardio Activity", 1, "20-30 minutes", 0, "Different from Day 1", 6);

        // Day 4
        createWorkoutDay(plan, 4, "Day 4", true, "Rest or Light Activity");

        // Day 5
        WorkoutDay day5 = createWorkoutDay(plan, 5, "Day 5", false, null);
        addExercise(day5, "Chair Pose", 2, "8-10", 60, "Controlled movements", 1);
        addExercise(day5, "Incline Push Up", 2, "AMRAP", 60, "Or Wall Push-Ups", 2);
        addExercise(day5, "Adductor Stretch Seated Bilateral Static", 2, "10-12", 60, "Resistance band or light dumbbells", 3);
        addExercise(day5, "Bird Dog", 2, "8-10 per side", 60, "Engage core, balance", 4);
        addExercise(day5, "Hand Plank", 2, "20-30s hold", 60, "From knees if needed", 5);
        addExercise(day5, "Cardio Activity", 1, "30-40 minutes (if tolerated)", 0, "Different from Day 1 & 3", 6);

        // Day 6
        createWorkoutDay(plan, 6, "Day 6", true, "Rest");
        // Day 7
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
        addExercise(day1, "Cardio Quick Feet", 2, "10-15", 90, "Seated Marches (in chair, light effort, mimicking marching motion); Using Quick Feet as a rhythmic seated movement", 1);
        addExercise(day1, "Push Up", 2, "AMRAP", 90, "Wall Push-Ups (or Incline Push-Ups if easier)", 2);
        addExercise(day1, "Chair Pose", 2, "5-8", 90, "Chair Stands (use arms for assistance)", 3);
        addExercise(day1, "Backward Arm Circle", 2, "8-10", 60, "Overhead Arm Raises (seated or standing, light/no weight)", 4);
        addExercise(day1, "Dumbbell Seated Calf Raise", 1, "10-12", 60, "Seated Bicep Curls (light weights or resistance band)", 5);
        addExercise(day1, "Dumbbell Overhead Tricep Extension", 1, "10-12", 60, "Overhead Tricep Extensions (light weight or resistance band, focus on triceps)", 6);
        addExercise(day1, "Water Aerobics", 1, "20-30 minutes", 0, "Highly recommended", 7);
        addExercise(day1, "Treadmill Walk", 1, "10-15 min (if no Water Aerobics)", 0, "", 8);

        // Day 2
        createWorkoutDay(plan, 2, "Day 2", true, "Rest");

        // Day 3
        WorkoutDay day3 = createWorkoutDay(plan, 3, "Day 3", false, null);
        addExercise(day3, "Seated Forward Bend", 2, "30-60s hold", 60, "Seated Forward Bend (modify by bending knees)", 1);
        addExercise(day3, "Seated Ulnar Nerve Slider", 2, "10-12", 60, "Seated Ulnar Nerve Slider (Adapt this arm movement along the wall as a seated wall slide)", 2);
        addExercise(day3, "Bird Dog", 2, "5-8 per side", 60, "Bird Dog (modify on knees)", 3);
        addExercise(day3, "Dumbbell Standing Hip Abduction", 2, "10-12 per side", 60, "Standing Hip Abduction (use chair for balance)", 4);
        addExercise(day3, "Treadmill Walk", 1, "10-15 minutes", 0, "Walking (gentle pace, flat surface, intervals if needed)", 5);
        addExercise(day3, "Water Aerobics", 1, "20-30 minutes", 0, "Highly recommended, low impact", 6);
        addExercise(day3, "Stationary Bike", 1, "10-15 min (if no Water Aerobics)", 0, "Light Resistance", 7);

        // Day 4
        createWorkoutDay(plan, 4, "Day 4", true, "Rest");

        // Day 5
        WorkoutDay day5 = createWorkoutDay(plan, 5, "Day 5", false, null);
        addExercise(day5, "Cardio Quick Feet", 2, "10-15", 90, "Seated Marches (in chair, light effort, mimicking marching motion); Using Quick Feet as a rhythmic seated movement", 1);
        addExercise(day5, "Push Up", 2, "AMRAP", 90, "Wall Push-Ups (or Incline Push-Ups if easier)", 2);
        addExercise(day5, "Chair Pose", 2, "5-8", 90, "Chair Stands (use arms for assistance)", 3);
        addExercise(day5, "Backward Arm Circle", 2, "8-10", 60, "Overhead Arm Raises (seated or standing, light/no weight)", 4);
        addExercise(day5, "Dumbbell Seated Calf Raise", 1, "10-12", 60, "Seated Bicep Curls (light weights or resistance band)", 5);
        addExercise(day5, "Dumbbell Overhead Tricep Extension", 1, "10-12", 60, "Overhead Tricep Extensions (light weight or resistance band, focus on triceps)", 6);
        addExercise(day5, "Water Aerobics", 1, "20-30 minutes", 0, "Highly recommended", 7);
        addExercise(day5, "Treadmill Walk", 1, "10-15 min (if no Water Aerobics)", 0, "", 8);

        // Day 6
        createWorkoutDay(plan, 6, "Day 6", true, "Rest");
        // Day 7
        createWorkoutDay(plan, 7, "Day 7", true, "Rest");

        // Add considerations
        plan.setConsiderations(List.of(
                PlanConsideration.builder().considerationText("Medical supervision highly recommended").plan(plan).build(),
                PlanConsideration.builder().considerationText("Start slowly, prioritize comfort").plan(plan).build(),
                PlanConsideration.builder().considerationText("Gradual progression").plan(plan).build(),
                PlanConsideration.builder().considerationText("Healthy diet").plan(plan).build(),
                PlanConsideration.builder().considerationText("Listen to your body").plan(plan).build()
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

    private void addExercise(WorkoutDay day, String name, int sets, String reps, int rest, String notes, int order) {
        Exercise exercise = Exercise.builder()
            .workoutDay(day)
            .exerciseName(name)
            .setsCount(sets)
            .reps(reps)
            .restSeconds(rest)
            .notes(notes)
            .exerciseOrder(order)
            .build();
        day.getExercises().add(exercise);
    }

}