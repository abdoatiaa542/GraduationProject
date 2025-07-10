package com.abdoatiia542.GraduationProject;

import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.User;
import com.abdoatiia542.GraduationProject.model.enumerations.*;
import com.abdoatiia542.GraduationProject.model.exercises.*;
import com.abdoatiia542.GraduationProject.repository.TraineeRepository;
import com.abdoatiia542.GraduationProject.repository.UserRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandLineRunnerBean {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PlansRepository plansRepository;
    private final PlanDaysRepository planDaysRepository;
    private final WorkoutSessionsRepository sessionRepository;
    private final ExerciseRepository exerciseRepository;
    private final BodyFocusRepository bodyFocusRepository;
    private final TraineeRepository traineeRepository;
    private final TraineePlanRepository traineePlanRepository;
    private final WorkoutSessionsRepository workoutSessionsRepository;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            log.info("⚙️ Command line runner started");

            log.info("✅ CommandLineRunnerBean initialized successfully");
        };
    }

    void insertUser() {
        if (userRepository.count() == 0) {

            Trainee trainee1 = Trainee.builder()
                    .username("abdo")
                    .email("abdo@example.com")
                    .password(passwordEncoder.encode("123456"))
                    .role(Role.TRAINEE)
                    .gender(Gender.MALE)
                    .activityLevel(ActivityLevel.ACTIVE)
                    .goal(Goal.STAY_FIT)
                    .birthYear(1999)
                    .enabled(true)
                    .height(175.0)
                    .weight(75.0)
                    .targetWeight(70.0)
                    .build();

            User trainee2 = User.builder()
                    .username("amer")
                    .email("amer@example.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.SUPER_ADMIN)
                    .gender(Gender.FEMALE)
//                    .activityLevel(ActivityLevel.ACTIVE)
//                    .goal(Goal.LOSE_WEIGHT)
                    .birthYear(2002)
                    .enabled(true)
//                    .height(165.0)
//                    .weight(60.0)
//                    .targetWeight(55.0)
                    .build();

            Trainee trainee3 = Trainee.builder()
                    .username("mohamed03")
                    .email("mohamed03@example.com")
                    .password(passwordEncoder.encode("pass1234"))
                    .role(Role.TRAINEE)
                    .gender(Gender.MALE)
                    .activityLevel(ActivityLevel.ACTIVE)
                    .goal(Goal.LOSE_WEIGHT)
                    .birthYear(1995)
                    .enabled(true)
                    .height(180.0)
                    .weight(90.0)
                    .targetWeight(80.0)
                    .build();

            userRepository.saveAll(List.of(trainee1, trainee2, trainee3));
        }
    }

    private void insertPlan1Data(PlansRepository plansRepo,
                                 PlanDaysRepository planDaysRepo,
                                 WorkoutSessionsRepository sessionRepo,
                                 ExerciseRepository exerciseRepo,
                                 BodyFocusRepository bodyFocusRepo) {

        // ✅ BodyFocus - Ensure they are managed entities
        BodyFocus legAndButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);
        BodyFocus cardio = getOrCreateBodyFocus("Cardio", bodyFocusRepo);
        BodyFocus chestMuscles = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus armShoulder = getOrCreateBodyFocus("Arm & Shoulder", bodyFocusRepo);
        BodyFocus sixPack = getOrCreateBodyFocus("Six Pack", bodyFocusRepo);
        BodyFocus backMuscles = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);

        // ✅ Exercises - Full Body A
        Exercise ex1 = createExercise(
                "Bodyweight Squat",
                "Squat down keeping your heels on the ground.",
                "8-10",
                2,
                60,
                35,
                70,
                Set.of(legAndButt, cardio)
        );

        Exercise ex2 = createExercise(
                "Incline Push Up",
                "Push up with hands elevated for beginners.",
                "AMRAP",
                2,
                60,
                25,
                50,
                Set.of(chestMuscles, armShoulder)
        );

        Exercise ex3 = createExercise(
                "Hand Plank",
                "Hold a straight plank from hands.",
                "20-30s hold",
                2,
                30,
                20,
                40,
                Set.of(sixPack, armShoulder)
        );

        WorkoutSessions fullBodyA = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Full Body A")
                        .image("https://example.com/images/full_body_a.png")
                        .trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(List.of(ex1, ex2, ex3))
                        .build()
        );

        // ✅ Exercises - Full Body B
        Exercise ex4 = createExercise(
                "Inverted Row",
                "Pull your chest to a bar/table.",
                "AMRAP",
                2,
                60,
                28,
                56,
                Set.of(backMuscles, armShoulder)
        );

        Exercise ex5 = createExercise(
                "Supermans",
                "Lift arms and legs while laying on stomach.",
                "10-12",
                2,
                60,
                26,
                52,
                Set.of(backMuscles, legAndButt)
        );

        WorkoutSessions fullBodyB = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Full Body B")
                        .image("https://example.com/images/full_body_b.png")
                        .trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(List.of(ex4, ex5))
                        .build()
        );

        // ✅ Plan & Days
        Plans plan = Plans.builder()
                .name("Beginner BMI Level 1")
                .goal("Build foundation, gain muscle")
                .trainingLevel(TrainingLevel.BEGINNER)
                .trainingSplit("Full Body")
                .build();

        List<PlanDays> days = List.of(
                PlanDays.builder().dayNumber(1).plan(plan).workoutSessions(List.of(fullBodyA)).build(),
                PlanDays.builder().dayNumber(2).plan(plan).note("Rest or Light Activity").build(),
                PlanDays.builder().dayNumber(3).plan(plan).workoutSessions(List.of(fullBodyB)).build(),
                PlanDays.builder().dayNumber(4).plan(plan).note("Rest").build()
        );

        plan.setPlanDays(days);
        plansRepo.save(plan);
    }


    private void insertPlan2Data(PlansRepository plansRepo,
                                 PlanDaysRepository planDaysRepo,
                                 WorkoutSessionsRepository sessionRepo,
                                 ExerciseRepository exerciseRepo,
                                 BodyFocusRepository bodyFocusRepo) {

        // ✅ BodyFocus
        BodyFocus legButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);
        BodyFocus sixPack = getOrCreateBodyFocus("Six Pack", bodyFocusRepo);
        BodyFocus armShoulder = getOrCreateBodyFocus("Arm & Shoulder", bodyFocusRepo);

        // ✅ Full Body A
        List<Exercise> fullBodyAExercises = List.of(
                createExercise("Dumbbell Goblet Squat", "Use appropriate weight", "8-10", 2, 60, 40, 80, Set.of(legButt)),
                createExercise("Dumbbell Bench Press", "Chest activation", "8-10", 2, 60, 30, 60, Set.of(chest)),
                createExercise("Dumbbell Row Unilateral", "Stable core, avoid twisting", "8-10 per side", 2, 60, 32, 64, Set.of(back)),
                createExercise("Bodyweight Assisted Pull Up", "Use assistance as needed", "AMRAP", 2, 60, 30, 60, Set.of(back)),
                createExercise("Hand Plank", "Straight line, core engaged", "30-45s hold", 2, 45, 22, 44, Set.of(sixPack)),
                createExercise("Glute Bridge", "Squeeze glutes", "12-15", 2, 60, 30, 60, Set.of(legButt))
        );

        WorkoutSessions fullBodyA = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Full Body A")
                        .trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(fullBodyAExercises)
                        .build()
        );

        // ✅ Full Body B
        List<Exercise> fullBodyBExercises = List.of(
                createExercise("Dumbbell Romanian Deadlift", "Hinge at hips, straight back", "10-12", 2, 60, 38, 76, Set.of(legButt)),
                createExercise("Dumbbell Overhead Press", "Control weight", "8-10", 2, 60, 30, 60, Set.of(armShoulder)),
                createExercise("Inverted Row", "Choose appropriate variation", "AMRAP", 2, 60, 28, 56, Set.of(back)),
                createExercise("Elbow Side Plank", "Straight line, core engaged", "30-45s hold per side", 2, 45, 24, 48, Set.of(sixPack)),
                createExercise("Bird Dog", "Engage core, balance", "10-12 per side", 2, 60, 25, 50, Set.of(sixPack)),
                createExercise("Dumbbell Curl", "Controlled movements", "10-12", 2, 60, 26, 52, Set.of(armShoulder))
        );

        WorkoutSessions fullBodyB = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Full Body B")
                        .trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(fullBodyBExercises)
                        .build()
        );

        // ✅ Full Body A - Repeat
        WorkoutSessions fullBodyARepeat = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Full Body A - Repeat")
                        .trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(fullBodyAExercises)
                        .build()
        );

        // ✅ Plan & Days
        Plans plan = Plans.builder()
                .name("Beginner BMI Level 2")
                .goal("Build strength & muscle, increase fitness")
                .trainingLevel(TrainingLevel.BEGINNER)
                .trainingSplit("Full Body")
                .build();

        List<PlanDays> days = List.of(
                PlanDays.builder().dayNumber(1).plan(plan).workoutSessions(List.of(fullBodyA)).build(),
                PlanDays.builder().dayNumber(2).plan(plan).note("Rest or Light Activity (yoga, walking, stretching)").build(),
                PlanDays.builder().dayNumber(3).plan(plan).workoutSessions(List.of(fullBodyB)).build(),
                PlanDays.builder().dayNumber(4).plan(plan).note("Rest or Light Activity").build(),
                PlanDays.builder().dayNumber(5).plan(plan).workoutSessions(List.of(fullBodyARepeat)).build(),
                PlanDays.builder().dayNumber(6).plan(plan).note("Rest").build(),
                PlanDays.builder().dayNumber(7).plan(plan).note("Rest").build()
        );

        plan.setPlanDays(days);
        plansRepo.save(plan);
    }

    private void insertPlan3Data(PlansRepository plansRepo,
                                 PlanDaysRepository planDaysRepo,
                                 WorkoutSessionsRepository sessionRepo,
                                 ExerciseRepository exerciseRepo,
                                 BodyFocusRepository bodyFocusRepo) {

        // ✅ BodyFocus
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);
        BodyFocus armShoulder = getOrCreateBodyFocus("Arm & Shoulder", bodyFocusRepo);
        BodyFocus legButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);

        // ✅ Upper Body A
        List<Exercise> upperAExercises = List.of(
                createExercise("Barbell Reverse Grip Bench Press", "Start with empty bar, gradual increase", "8-10", 2, 90, 35, 70, Set.of(chest)),
                createExercise("Dumbbell Row Bilateral", "Stable core, flat back", "8-10 per side", 2, 90, 32, 64, Set.of(back)),
                createExercise("Dumbbell Overhead Press", "Control weight, gradual increase", "8-10", 2, 90, 30, 60, Set.of(armShoulder)),
                createExercise("Bodyweight Assisted Pull Up", "Decrease assistance/increase weight", "AMRAP", 2, 90, 30, 60, Set.of(back)),
                createExercise("Dumbbell Curl", "Controlled movements", "10-12", 2, 60, 26, 52, Set.of(armShoulder)),
                createExercise("Dumbbell Seated Overhead Tricep Extension", "Elbows close to head", "10-12", 2, 60, 27, 54, Set.of(armShoulder))
        );

        WorkoutSessions upperBodyA = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Upper Body A")
                        .trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(upperAExercises)
                        .build()
        );

        // ✅ Lower Body A
        List<Exercise> lowerAExercises = List.of(
                createExercise("Barbell Squat", "Start with empty bar, gradual increase", "8-10", 2, 90, 38, 76, Set.of(legButt)),
                createExercise("Dumbbell Romanian Deadlift", "Hinge at hips, straight back", "10-12", 2, 90, 36, 72, Set.of(legButt)),
                createExercise("Machine Leg Press", "Control weight, don't lock knees", "10-12", 2, 60, 34, 68, Set.of(legButt)),
                createExercise("Machine Leg Extension", "Controlled, no momentum", "12-15", 2, 60, 30, 60, Set.of(legButt)),
                createExercise("Machine Seated Leg Curl", "Squeeze hamstrings", "12-15", 2, 60, 30, 60, Set.of(legButt)),
                createExercise("Machine Standing Calf Raises", "", "15-20", 2, 60, 28, 56, Set.of(legButt))
        );

        WorkoutSessions lowerBodyA = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Lower Body A")
                        .trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(lowerAExercises)
                        .build()
        );

        // ✅ Upper Body B
        List<Exercise> upperBExercises = List.of(
                createExercise("Dumbbell Bench Press", "Muscle-mind connection", "12-15", 2, 60, 30, 60, Set.of(chest)),
                createExercise("Dumbbell Row Bilateral", "Lighter than Day 1", "12-15", 2, 60, 30, 60, Set.of(back)),
                createExercise("Dumbbell Lateral Raise", "Shoulder stability", "12-15", 2, 60, 28, 56, Set.of(armShoulder)),
                createExercise("Cable Rope Face Pulls", "Light weight, rear deltoids", "15-20", 2, 60, 26, 52, Set.of(armShoulder)),
                createExercise("Dumbbell Curl", "", "12-15", 2, 60, 26, 52, Set.of(armShoulder)),
                createExercise("Dumbbell Overhead Tricep Extension", "", "12-15", 2, 60, 27, 54, Set.of(armShoulder))
        );

        WorkoutSessions upperBodyB = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Upper Body B")
                        .trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(upperBExercises)
                        .build()
        );

        // ✅ Lower Body B
        List<Exercise> lowerBExercises = List.of(
                createExercise("Walking Lunge", "Balance and control", "10-12 per leg", 2, 60, 35, 70, Set.of(legButt)),
                createExercise("Glute Bridge", "Glute activation", "15-20", 2, 60, 30, 60, Set.of(legButt)),
                createExercise("Machine Seated Leg Curl", "Squeeze hamstrings", "15-20", 2, 60, 30, 60, Set.of(legButt)),
                createExercise("Machine Leg Extension", "Controlled movements", "15-20", 2, 60, 30, 60, Set.of(legButt)),
                createExercise("Machine Standing Calf Raises", "", "20-25", 2, 60, 28, 56, Set.of(legButt))
        );

        WorkoutSessions lowerBodyB = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Lower Body B")
                        .trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(lowerBExercises)
                        .build()
        );

        // ✅ Plan & Days
        Plans plan = Plans.builder()
                .name("BMI Level 3")
                .goal("Increase strength & muscle, improve fitness")
                .trainingLevel(TrainingLevel.INTERMEDIATE)
                .trainingSplit("Upper/Lower")
                .build();

        List<PlanDays> days = List.of(
                PlanDays.builder().dayNumber(1).plan(plan).workoutSessions(List.of(upperBodyA)).build(),
                PlanDays.builder().dayNumber(2).plan(plan).workoutSessions(List.of(lowerBodyA)).build(),
                PlanDays.builder().dayNumber(3).plan(plan).note("Rest or Active Recovery").build(),
                PlanDays.builder().dayNumber(4).plan(plan).workoutSessions(List.of(upperBodyB)).build(),
                PlanDays.builder().dayNumber(5).plan(plan).workoutSessions(List.of(lowerBodyB)).build(),
                PlanDays.builder().dayNumber(6).plan(plan).note("Rest").build(),
                PlanDays.builder().dayNumber(7).plan(plan).note("Rest").build()
        );

        plan.setPlanDays(days);
        plansRepo.save(plan);
    }
    private void insertPlan4Data(PlansRepository plansRepo,
                                 PlanDaysRepository planDaysRepo,
                                 WorkoutSessionsRepository sessionRepo,
                                 ExerciseRepository exerciseRepo,
                                 BodyFocusRepository bodyFocusRepo) {

        // ✅ Body Focuses
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);
        BodyFocus armShoulder = getOrCreateBodyFocus("Arm & Shoulder", bodyFocusRepo);
        BodyFocus legButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);

        // ✅ Upper Body A
        List<Exercise> upperAExercises = List.of(
                createExercise("Barbell Reverse Grip Bench Press", "Controlled movements, full ROM", "8-12", 3, 90, 35, 105, Set.of(chest)),
                createExercise("Dumbbell Row Bilateral", "Stable core", "8-12", 3, 90, 32, 96, Set.of(back)),
                createExercise("Dumbbell Overhead Press", "Control weight", "8-12", 3, 90, 30, 90, Set.of(armShoulder)),
                createExercise("Chin Ups", "Or Lat Pulldowns (918)", "AMRAP", 3, 90, 34, 102, Set.of(back, armShoulder)),
                createExercise("Dumbbell Curl", "Control weight", "10-15", 3, 60, 26, 78, Set.of(armShoulder)),
                createExercise("Cable Rope Pushdown", "Elbows close to body", "12-15", 3, 60, 27, 81, Set.of(armShoulder))
        );

        WorkoutSessions upperBodyA = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Upper Body A")
                        .trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(upperAExercises)
                        .build()
        );

        // ✅ Lower Body A
        List<Exercise> lowerAExercises = List.of(
                createExercise("Barbell Squat", "Proper form, deep as comfortable", "8-12", 3, 120, 38, 114, Set.of(legButt)),
                createExercise("Dumbbell Romanian Deadlift", "Hinge at hips, straight back", "10-15", 3, 120, 36, 108, Set.of(legButt, back)),
                createExercise("Machine Leg Press", "Control weight", "10-15", 3, 90, 34, 102, Set.of(legButt)),
                createExercise("Machine Leg Extension", "Control movement", "12-15", 3, 60, 30, 90, Set.of(legButt)),
                createExercise("Machine Seated Leg Curl", "Squeeze hamstrings", "12-15", 3, 60, 30, 90, Set.of(legButt)),
                createExercise("Dumbbell Calf Raise", "Full ROM", "15-20", 3, 60, 28, 84, Set.of(legButt))
        );

        WorkoutSessions lowerBodyA = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Lower Body A")
                        .trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(lowerAExercises)
                        .build()
        );

        // ✅ Upper Body B
        List<Exercise> upperBExercises = List.of(
                createExercise("Dumbbell Bench Press", "Lighter than Day 1", "12-15", 3, 75, 30, 90, Set.of(chest)),
                createExercise("Dumbbell Row Bilateral", "Lighter than Day 1", "12-15", 3, 75, 30, 90, Set.of(back)),
                createExercise("Dumbbell Arnold Press", "Controlled movements", "10-15", 3, 75, 30, 90, Set.of(armShoulder)),
                createExercise("Cable Rope Face Pulls", "Light weight, rear deltoids", "15-20", 3, 45, 26, 78, Set.of(armShoulder, back)),
                createExercise("Dumbbell Curl", "Lighter than Day 1", "15-20", 2, 45, 26, 52, Set.of(armShoulder)),
                createExercise("Dumbbell Overhead Tricep Extension", "Lighter than Day 1", "15-20", 2, 45, 27, 54, Set.of(armShoulder))
        );

        WorkoutSessions upperBodyB = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Upper Body B")
                        .trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(upperBExercises)
                        .build()
        );

        // ✅ Lower Body B
        List<Exercise> lowerBExercises = List.of(
                createExercise("Walking Lunge", "Balance and control", "10-12 per leg", 3, 75, 35, 105, Set.of(legButt)),
                createExercise("Glute Bridge", "Squeeze glutes", "15-20", 3, 75, 30, 90, Set.of(legButt)),
                createExercise("Machine Seated Leg Curl", "Squeeze hamstrings", "15-20", 3, 45, 30, 90, Set.of(legButt)),
                createExercise("Machine Leg Extension", "Controlled movements", "15-20", 3, 45, 30, 90, Set.of(legButt)),
                createExercise("Calf Raises", "Full ROM", "20-25", 3, 45, 28, 84, Set.of(legButt))
        );

        WorkoutSessions lowerBodyB = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Lower Body B")
                        .trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(lowerBExercises)
                        .build()
        );

        // ✅ Plan & Days
        Plans plan = Plans.builder()
                .name("BMI Level 4")
                .goal("Balanced fitness (strength, hypertrophy, endurance)")
                .trainingLevel(TrainingLevel.INTERMEDIATE)
                .trainingSplit("Upper/Lower")
                .build();

        List<PlanDays> days = List.of(
                PlanDays.builder().dayNumber(1).plan(plan).workoutSessions(List.of(upperBodyA)).build(),
                PlanDays.builder().dayNumber(2).plan(plan).workoutSessions(List.of(lowerBodyA)).build(),
                PlanDays.builder().dayNumber(3).plan(plan).note("Rest or Active Recovery (light cardio, stretching, yoga)").build(),
                PlanDays.builder().dayNumber(4).plan(plan).workoutSessions(List.of(upperBodyB)).build(),
                PlanDays.builder().dayNumber(5).plan(plan).workoutSessions(List.of(lowerBodyB)).build(),
                PlanDays.builder().dayNumber(6).plan(plan).note("Rest").build(),
                PlanDays.builder().dayNumber(7).plan(plan).note("Rest").build()
        );

        plan.setPlanDays(days);
        plansRepo.save(plan);
    }

    private void insertPlan5Data(PlansRepository plansRepo,
                                 PlanDaysRepository planDaysRepo,
                                 WorkoutSessionsRepository sessionRepo,
                                 ExerciseRepository exerciseRepo,
                                 BodyFocusRepository bodyFocusRepo) {

        // ✅ Body Focus
        BodyFocus leg = getOrCreateBodyFocus("Leg", bodyFocusRepo);
        BodyFocus butt = getOrCreateBodyFocus("Butt", bodyFocusRepo);
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);
        BodyFocus arm = getOrCreateBodyFocus("Arm", bodyFocusRepo);
        BodyFocus shoulder = getOrCreateBodyFocus("Shoulder", bodyFocusRepo);
        BodyFocus sixPack = getOrCreateBodyFocus("Six Pack", bodyFocusRepo);
        BodyFocus core = getOrCreateBodyFocus("Core", bodyFocusRepo);
        BodyFocus cardio = getOrCreateBodyFocus("Cardio", bodyFocusRepo);

        // ✅ Full Body A + Cardio
        List<Exercise> fullBodyAExercises = List.of(
                createExercise("Dumbbell Goblet Squat", "Or Barbell Squats (572)", "10-12", 3, 90, 38, 114, Set.of(leg, butt)),
                createExercise("Dumbbell Bench Press", "Focus on chest activation", "10-12", 3, 90, 35, 105, Set.of(chest)),
                createExercise("Dumbbell Row Bilateral", "Keep core engaged", "10-12 per side", 3, 90, 34, 102, Set.of(back)),
                createExercise("Dumbbell Overhead Press", "Dumbbells or barbells", "10-12", 3, 90, 30, 90, Set.of(arm, shoulder)),
                createExercise("Hand Plank", "Engage core", "30-60s hold", 3, 60, 24, 72, Set.of(sixPack, core))
        );

        WorkoutSessions fullBodyA = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Full Body A + Cardio")
                        .trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(fullBodyAExercises)
                        .build()
        );

        // ✅ Full Body B + Cardio
        List<Exercise> fullBodyBExercises = List.of(
                createExercise("Dumbbell Romanian Deadlift", "Hinge at hips, straight back", "10-12", 3, 90, 36, 108, Set.of(leg, butt)),
                createExercise("Dumbbell Reverse Lunge", "Maintain balance", "10-12 per leg", 3, 90, 36, 108, Set.of(leg, butt)),
                createExercise("Dumbbell Curl", "Controlled movement", "12-15", 3, 60, 26, 78, Set.of(arm, shoulder)),
                createExercise("Dumbbell Overhead Tricep Extension", "Elbows close to head", "12-15", 3, 60, 27, 81, Set.of(arm, shoulder)),
                createExercise("Bicycle Crunch", "Engage core", "15-20 per side", 3, 60, 28, 84, Set.of(sixPack, core))
        );

        WorkoutSessions fullBodyB = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Full Body B + Cardio")
                        .trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(fullBodyBExercises)
                        .build()
        );

        // ✅ Full Body C + Cardio
        List<Exercise> fullBodyCExercises = List.of(
                createExercise("Bodyweight Squat", "", "15", 2, 30, 32, 64, Set.of(leg, butt)),
                createExercise("Push Up", "Or Incline Push-Ups", "AMRAP", 2, 30, 28, 56, Set.of(chest, arm, shoulder)),
                createExercise("Dumbbell Row Bilateral", "", "10-12 per side", 2, 30, 32, 64, Set.of(back)),
                createExercise("Cardio Jumping Jacks", "", "20", 2, 30, 30, 60, Set.of(cardio)),
                createExercise("Mountain Climber", "", "15-20 per side", 2, 30, 28, 56, Set.of(cardio, core))
        );

        WorkoutSessions fullBodyC = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Full Body C + Cardio")
                        .trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(fullBodyCExercises)
                        .build()
        );

        // ✅ Plan & Days
        Plans plan = Plans.builder()
                .name("BMI Level 5")
                .goal("Improve cardio, build muscle, burn calories")
                .trainingLevel(TrainingLevel.INTERMEDIATE)
                .trainingSplit("Full Body with Cardio")
                .build();

        List<PlanDays> days = List.of(
                PlanDays.builder().dayNumber(1).plan(plan).workoutSessions(List.of(fullBodyA)).build(),
                PlanDays.builder().dayNumber(2).plan(plan).note("Rest or Light Activity (walking, stretching)").build(),
                PlanDays.builder().dayNumber(3).plan(plan).workoutSessions(List.of(fullBodyB)).build(),
                PlanDays.builder().dayNumber(4).plan(plan).note("Rest or Light Activity").build(),
                PlanDays.builder().dayNumber(5).plan(plan).workoutSessions(List.of(fullBodyC)).build(),
                PlanDays.builder().dayNumber(6).plan(plan).note("Rest").build(),
                PlanDays.builder().dayNumber(7).plan(plan).note("Rest").build()
        );

        plan.setPlanDays(days);
        plansRepo.save(plan);
    }

    private void insertPlan6Data(PlansRepository plansRepo,
                                 PlanDaysRepository planDaysRepo,
                                 WorkoutSessionsRepository sessionRepo,
                                 ExerciseRepository exerciseRepo,
                                 BodyFocusRepository bodyFocusRepo) {

        // ✅ Body Focus
        BodyFocus legButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus sixPack = getOrCreateBodyFocus("Six Pack", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);

        // ✅ Full Body A + Cardio
        List<Exercise> fullBodyA = List.of(
                createExercise("Chair Pose", "Controlled movements", "8-10", 2, 60, 18, 36, Set.of(legButt)),
                createExercise("Incline Push Up", "Or Wall Push-Ups", "AMRAP", 2, 60, 20, 40, Set.of(chest)),
                createExercise("Adductor Stretch Seated Bilateral Static", "Resistance band or light dumbbells", "10-12", 2, 60, 12, 24, Set.of(legButt)),
                createExercise("Bird Dog", "Engage core, balance", "8-10 per side", 2, 60, 16, 32, Set.of(sixPack)),
                createExercise("Hand Plank", "From knees if needed", "20-30s hold", 2, 60, 14, 28, Set.of(sixPack))
        );

        WorkoutSessions sessionA = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Full Body A + Cardio")
                        .trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(fullBodyA)
                        .build()
        );

        // ✅ Full Body B + Cardio
        List<Exercise> fullBodyB = List.of(
                createExercise("Chair Pose", "", "8-10", 2, 60, 18, 36, Set.of(legButt)),
                createExercise("Incline Push Up", "Or Wall Push-Ups", "AMRAP", 2, 60, 20, 40, Set.of(chest)),
                createExercise("Band Pull Apart", "Squeeze shoulder blades", "15-20", 2, 60, 15, 30, Set.of(back)),
                createExercise("Bird Dog", "", "8-10 per side", 2, 60, 16, 32, Set.of(sixPack)),
                createExercise("Elbow Side Plank", "From knees if needed", "20-30s hold per side", 2, 60, 14, 28, Set.of(sixPack))
        );

        WorkoutSessions sessionB = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Full Body B + Cardio")
                        .trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(fullBodyB)
                        .build()
        );

        // ✅ Full Body C + Cardio
        List<Exercise> fullBodyC = List.of(
                createExercise("Chair Pose", "Controlled movements", "8-10", 2, 60, 18, 36, Set.of(legButt)),
                createExercise("Incline Push Up", "Or Wall Push-Ups", "AMRAP", 2, 60, 20, 40, Set.of(chest)),
                createExercise("Adductor Stretch Seated Bilateral Static", "Resistance band or light dumbbells", "10-12", 2, 60, 12, 24, Set.of(legButt)),
                createExercise("Bird Dog", "Engage core, balance", "8-10 per side", 2, 60, 16, 32, Set.of(sixPack)),
                createExercise("Hand Plank", "From knees if needed", "20-30s hold", 2, 60, 14, 28, Set.of(sixPack))
        );

        WorkoutSessions sessionC = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Full Body C + Cardio")
                        .trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(fullBodyC)
                        .build()
        );

        // ✅ Plan & Days
        Plans plan = Plans.builder()
                .name("BMI Level 6")
                .goal("Improve cardio, strength, mobility; build foundation")
                .trainingLevel(TrainingLevel.BEGINNER)
                .trainingSplit("Full Body with Cardio")
                .build();

        List<PlanDays> days = List.of(
                PlanDays.builder().dayNumber(1).plan(plan).workoutSessions(List.of(sessionA)).build(),
                PlanDays.builder().dayNumber(2).plan(plan).note("Rest or Light Activity (gentle stretching, short walk)").build(),
                PlanDays.builder().dayNumber(3).plan(plan).workoutSessions(List.of(sessionB)).build(),
                PlanDays.builder().dayNumber(4).plan(plan).note("Rest or Light Activity").build(),
                PlanDays.builder().dayNumber(5).plan(plan).workoutSessions(List.of(sessionC)).build(),
                PlanDays.builder().dayNumber(6).plan(plan).note("Rest").build(),
                PlanDays.builder().dayNumber(7).plan(plan).note("Rest").build()
        );

        plan.setPlanDays(days);
        plansRepo.save(plan);
    }
    private void insertPlan7Data(PlansRepository plansRepo,
                                 PlanDaysRepository planDaysRepo,
                                 WorkoutSessionsRepository sessionRepo,
                                 ExerciseRepository exerciseRepo,
                                 BodyFocusRepository bodyFocusRepo) {

        // ✅ Body Focuses
        BodyFocus cardio = getOrCreateBodyFocus("Cardio", bodyFocusRepo);
        BodyFocus legButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus armShoulder = getOrCreateBodyFocus("Arm & Shoulder", bodyFocusRepo);
        BodyFocus sixPack = getOrCreateBodyFocus("Six Pack", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);

        // ✅ Full Body A
        List<Exercise> fullBodyA = List.of(
                createExercise("Cardio Quick Feet", "Seated Marches (light effort, mimicking marching motion)", "10-15", 2, 90, 18, 36, Set.of(cardio, legButt)),
                createExercise("Push Up", "Wall Push-Ups (or Incline Push-Ups if easier)", "AMRAP", 2, 90, 20, 40, Set.of(chest, armShoulder)),
                createExercise("Chair Pose", "Chair Stands (use arms for assistance)", "5-8", 2, 90, 16, 32, Set.of(legButt)),
                createExercise("Backward Arm Circle", "Overhead Arm Raises (seated or standing)", "8-10", 2, 60, 12, 24, Set.of(armShoulder)),
                createExercise("Dumbbell Seated Calf Raise", "Seated Bicep Curls (light weights or resistance band)", "10-12", 1, 60, 10, 10, Set.of(legButt)),
                createExercise("Dumbbell Overhead Tricep Extension", "Overhead Tricep Extensions (light weight or resistance band)", "10-12", 1, 60, 10, 10, Set.of(armShoulder))
        );

        WorkoutSessions sessionA = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Full Body A")
                        .trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(fullBodyA)
                        .build()
        );

        // ✅ Mobility & Core
        List<Exercise> mobilityCore = List.of(
                createExercise("Seated Forward Bend", "Modify by bending knees", "30-60s hold", 2, 60, 12, 24, Set.of(sixPack)),
                createExercise("Seated Ulnar Nerve Slider", "Adapt along wall as seated wall slide", "10-12", 2, 60, 10, 20, Set.of(armShoulder)),
                createExercise("Bird Dog", "Modify on knees", "5-8 per side", 2, 60, 14, 28, Set.of(sixPack)),
                createExercise("Dumbbell Standing Hip Abduction", "Use chair for balance", "10-12 per side", 2, 60, 16, 32, Set.of(legButt)),
                createExercise("Treadmill Walk", "Gentle pace, flat surface", null, null, null, 30, 30, Set.of(cardio))
        );

        WorkoutSessions sessionB = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Mobility & Core")
                        .trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(mobilityCore)
                        .build()
        );

        // ✅ Repeat Full Body A
        WorkoutSessions repeatSessionA = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Repeat Full Body A")
                        .trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(fullBodyA)
                        .build()
        );

        // ✅ Plan & Days
        Plans plan = Plans.builder()
                .name("BMI Level 7")
                .goal("Improve mobility, increase activity, enhance cardio")
                .trainingLevel(TrainingLevel.BEGINNER)
                .trainingSplit("Full Body")
                .build();

        List<PlanDays> days = List.of(
                PlanDays.builder().dayNumber(1).plan(plan).workoutSessions(List.of(sessionA)).build(),
                PlanDays.builder().dayNumber(2).plan(plan).note("Rest").build(),
                PlanDays.builder().dayNumber(3).plan(plan).workoutSessions(List.of(sessionB)).build(),
                PlanDays.builder().dayNumber(4).plan(plan).note("Rest").build(),
                PlanDays.builder().dayNumber(5).plan(plan).workoutSessions(List.of(repeatSessionA)).build(),
                PlanDays.builder().dayNumber(6).plan(plan).note("Rest").build(),
                PlanDays.builder().dayNumber(7).plan(plan).note("Rest").build()
        );

        plan.setPlanDays(days);
        plansRepo.save(plan);
    }
    private void insertPlan8Data(PlansRepository plansRepo,
                                 PlanDaysRepository planDaysRepo,
                                 WorkoutSessionsRepository sessionRepo,
                                 ExerciseRepository exerciseRepo,
                                 BodyFocusRepository bodyFocusRepo) {

        // ✅ Body Focuses
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);
        BodyFocus armShoulder = getOrCreateBodyFocus("Arm & Shoulder", bodyFocusRepo);
        BodyFocus legButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);
        BodyFocus sixPack = getOrCreateBodyFocus("Six Pack", bodyFocusRepo);
        BodyFocus cardio = getOrCreateBodyFocus("Cardio", bodyFocusRepo);

        // ✅ Push A
        List<Exercise> pushA = List.of(
                createExercise("Barbell Bench Press", "Heavy set", "6-8", 4, 120, 42, 168, Set.of(chest)),
                createExercise("Standing Overhead Press", "Barbell or Dumbbells", "8-10", 4, 90, 36, 144, Set.of(armShoulder)),
                createExercise("Incline Dumbbell Press", "Upper chest focus", "10-12", 3, 90, 34, 102, Set.of(chest)),
                createExercise("Cable Lateral Raises", "Strict form", "12-15", 3, 60, 28, 84, Set.of(armShoulder)),
                createExercise("Tricep Dips", "Weighted if advanced", "10-12", 3, 90, 30, 90, Set.of(armShoulder))
        );
        WorkoutSessions pushSessionA = sessionRepo.save(
                WorkoutSessions.builder().name("Push A").trainingLevel(TrainingLevel.ADVANCED).exercises(pushA).build()
        );

        // ✅ Pull A
        List<Exercise> pullA = List.of(
                createExercise("Barbell Deadlift", "Heavy", "5-6", 4, 150, 50, 200, Set.of(back)),
                createExercise("Pull Ups", "Weighted if possible", "AMRAP", 4, 90, 34, 136, Set.of(back, armShoulder)),
                        createExercise("Pull Ups", "Weighted if possible", "AMRAP", 4, 90, 34, 136, Set.of(back, armShoulder)),
                        createExercise("Barbell Row", "Strict back control", "8-10", 4, 90, 36, 144, Set.of(back)),
                        createExercise("Face Pulls", "Rear delt focus", "15-20", 3, 60, 28, 84, Set.of(back, armShoulder)),
                        createExercise("Barbell Curl", "Heavy", "10-12", 3, 60, 30, 90, Set.of(armShoulder))
                );
        WorkoutSessions pullSessionA = sessionRepo.save(
                WorkoutSessions.builder().name("Pull A").trainingLevel(TrainingLevel.ADVANCED).exercises(pullA).build()
        );

        // ✅ Legs A
        List<Exercise> legsA = List.of(
                createExercise("Barbell Back Squat", "Heavy, below parallel", "6-8", 4, 150, 45, 180, Set.of(legButt)),
                createExercise("Romanian Deadlift", "Hamstring focus", "8-10", 4, 120, 40, 160, Set.of(legButt, back)),
                createExercise("Walking Lunges", "With Dumbbells", "12 per leg", 3, 90, 36, 108, Set.of(legButt)),
                createExercise("Calf Raise", "Slow & controlled", "15-20", 4, 60, 28, 112, Set.of(legButt))
        );
        WorkoutSessions legsSessionA = sessionRepo.save(
                WorkoutSessions.builder().name("Legs A").trainingLevel(TrainingLevel.ADVANCED).exercises(legsA).build()
        );

        // ✅ Core & Cardio
        List<Exercise> coreCardio = List.of(
                createExercise("Plank", "Hold tight core", "60s hold", 3, 60, 24, 72, Set.of(sixPack)),
                createExercise("Bicycle Crunch", "Fast tempo", "20 per side", 3, 60, 26, 78, Set.of(sixPack)),
                createExercise("Mountain Climbers", "Fast pace", "20 per side", 3, 45, 28, 84, Set.of(cardio, sixPack)),
                createExercise("Burpees", "Explosive", "15", 3, 60, 35, 105, Set.of(cardio))
        );
        WorkoutSessions coreCardioSession = sessionRepo.save(
                WorkoutSessions.builder().name("Core & Cardio").trainingLevel(TrainingLevel.ADVANCED).exercises(coreCardio).build()
        );

        // ✅ Push B
        List<Exercise> pushB = List.of(
                createExercise("Dumbbell Shoulder Press", "Alternate arms", "8-10", 4, 90, 34, 136, Set.of(armShoulder)),
                createExercise("Flat Dumbbell Press", "Medium weight", "10-12", 4, 90, 35, 140, Set.of(chest)),
                createExercise("Overhead Tricep Extension", "Dumbbell", "12-15", 3, 60, 26, 78, Set.of(armShoulder)),
                createExercise("Chest Fly (Machine)", "Controlled", "12-15", 3, 60, 28, 84, Set.of(chest))
        );
        WorkoutSessions pushSessionB = sessionRepo.save(
                WorkoutSessions.builder().name("Push B").trainingLevel(TrainingLevel.ADVANCED).exercises(pushB).build()
        );

        // ✅ Legs B
        List<Exercise> legsB = List.of(
                createExercise("Front Squat", "Upright posture", "8-10", 4, 120, 42, 168, Set.of(legButt)),
                createExercise("Glute Bridge", "Barbell or Dumbbell", "12-15", 3, 90, 36, 108, Set.of(legButt)),
                createExercise("Leg Extension", "Machine", "12-15", 3, 60, 30, 90, Set.of(legButt)),
                createExercise("Calf Raise", "Single leg variation", "15", 3, 45, 28, 84, Set.of(legButt))
        );
        WorkoutSessions legsSessionB = sessionRepo.save(
                WorkoutSessions.builder().name("Legs B").trainingLevel(TrainingLevel.ADVANCED).exercises(legsB).build()
        );

        // ✅ Plan & Days
        Plans plan = Plans.builder()
                .name("BMI Level 8")
                .goal("Build advanced strength, hypertrophy & endurance")
                .trainingSplit("Push / Pull / Legs + Core")
                .trainingLevel(TrainingLevel.ADVANCED)
                .build();

        List<PlanDays> days = List.of(
                PlanDays.builder().dayNumber(1).plan(plan).workoutSessions(List.of(pushSessionA)).build(),
                PlanDays.builder().dayNumber(2).plan(plan).workoutSessions(List.of(pullSessionA)).build(),
                PlanDays.builder().dayNumber(3).plan(plan).workoutSessions(List.of(legsSessionA)).build(),
                PlanDays.builder().dayNumber(4).plan(plan).workoutSessions(List.of(coreCardioSession)).build(),
                PlanDays.builder().dayNumber(5).plan(plan).workoutSessions(List.of(pushSessionB)).build(),
                PlanDays.builder().dayNumber(6).plan(plan).workoutSessions(List.of(legsSessionB)).build(),
                PlanDays.builder().dayNumber(7).plan(plan).note("Rest or Active Recovery").build()
        );

        plan.setPlanDays(days);
        plansRepo.save(plan);
    }



    private BodyFocus getOrCreateBodyFocus(String name, BodyFocusRepository repo) {
        return repo.findByNameIgnoreCase(name).orElseGet(() -> repo.save(BodyFocus.builder().name(name).build()));
    }

    private BodyFocus createBodyFocus(String name) {
        if (bodyFocusRepository.findByNameIgnoreCase(name).isPresent()) {
            return bodyFocusRepository.findByNameIgnoreCase(name).get();
        }

        BodyFocus bodyFocus = BodyFocus.builder()
                .name(name)
                .build();
        return bodyFocusRepository.save(bodyFocus);
    }
    private Exercise createExercise(String name, String description, String reps,
                                    Integer sets, Integer restSeconds, int caloriesBurned, int totalCalories,
                                    Set<BodyFocus> focuses) {

        // check by DB if name exists ignoring case
        Optional<Exercise> existing = exerciseRepository.findByNameIgnoreCase(name.trim());

        if (existing.isPresent()) {
            log.info("⛔ Skipping duplicate exercise: {}", name);
            return existing.get(); // ✅ موجود بالفعل، رجعه
        }

        // ✅ مش موجود، خزنه
        Exercise ex = Exercise.builder()
                .name(name.trim())
                .description(description)
                .reps(reps)
                .sets(sets)
                .durationRestSeconds(restSeconds)
                .caloriesBurned(caloriesBurned)
                .totalCalories(totalCalories)
                .bodyFocuses(focuses)
                .build();

        return exerciseRepository.save(ex);
    }


    private WorkoutSessions createWorkoutSession(String name, String description,
                                                 TrainingLevel level, List<Exercise> exercises) {

        Optional<WorkoutSessions> existing =workoutSessionsRepository.findByNameIgnoreCase(name.trim());
        if (existing.isPresent()) {
            return existing.get();
        }

        WorkoutSessions session = WorkoutSessions.builder()
                .name(name)
                .description(description)
                .trainingLevel(level)
                .exercises(exercises)
                .build();

        return workoutSessionsRepository.save(session);
    }

    private Plans createPlan(String name, String goal, TrainingLevel level, String split) {
        Plans plan = Plans.builder()
                .name(name)
                .goal(goal)
                .trainingLevel(level)
                .trainingSplit(split)
                .build();

        return plansRepository.save(plan);
    }

    private PlanDays createPlanDay(int dayNumber, String note, Plans plan,
                                   List<WorkoutSessions> sessions) {
        PlanDays planDay = PlanDays.builder()
                .dayNumber(dayNumber)
                .note(note)
                .plan(plan)
                .workoutSessions(sessions)
                .build();

        return planDaysRepository.save(planDay);
    }
    private String normalizeName(String name) {
        return name.trim().toLowerCase().replaceAll("[\\s\\-]", "");
    }

    private Trainee createTestTrainee() {
        // تحقق إذا كان موجود
        Optional<Trainee> existingTrainee = traineeRepository.findByEmailIgnoreCase("test@example.com");
        if (existingTrainee.isPresent()) {
            return existingTrainee.get();
        }

        Trainee trainee = Trainee.builder()
                .email("test@example.com")
//                .name("Test Trainee")
//                .age(25)
                .weight(70.0)
                .height(175.0)
                // أضف باقي الـ fields اللي محتاجها
                .build();

        return traineeRepository.save(trainee);
    }

    private TraineePlan createTraineePlan(Trainee trainee, Plans plan, LocalDate startDate) {
        TraineePlan traineePlan = TraineePlan.builder()
                .trainee(trainee)
                .plan(plan)
                .startDate(startDate)
                .build();

        return traineePlanRepository.save(traineePlan);
    }
    private void insertQuickTestData() {
        // 1. إنشاء BodyFocus بسيط
        BodyFocus chest = BodyFocus.builder().name("Chest").build();
        bodyFocusRepository.save(chest);

        // 2. إنشاء Exercise بسيط
        Exercise pushUps = Exercise.builder()
                .name("Push Ups")
                .description("Basic push ups")
                .reps("3x15")
                .sets(3)
                .durationSeconds(30)
                .caloriesBurned(5)
                .totalCalories(15)
                .durationRestSeconds(60)
                .bodyFocuses(Set.of(chest))
                .build();
        exerciseRepository.save(pushUps);

        // 3. إنشاء WorkoutSession
        WorkoutSessions session = WorkoutSessions.builder()
                .name("Upper Body")
                .description("Upper body workout")
                .trainingLevel(TrainingLevel.BEGINNER)
                .exercises(List.of(pushUps))
                .build();
        sessionRepository.save(session);

        // 4. إنشاء Plan
        Plans plan = Plans.builder()
                .name("Test Plan")
                .goal("Test goal")
                .trainingLevel(TrainingLevel.BEGINNER)
                .trainingSplit("3 days")
                .build();
        plansRepository.save(plan);

        // 5. إنشاء PlanDay
        PlanDays day1 = PlanDays.builder()
                .dayNumber(1)
                .note("Day 1 workout")
                .plan(plan)
                .workoutSessions(List.of(session))
                .build();
        planDaysRepository.save(day1);

        // 6. إنشاء Trainee بسيط
        Trainee trainee = Trainee.builder()
                .email("test@test.com")
//                .name("Test User")
                .build();
        traineeRepository.save(trainee);

        // 7. إنشاء TraineePlan
        TraineePlan traineePlan = TraineePlan.builder()
                .trainee(trainee)
                .plan(plan)
                .startDate(LocalDate.now())
                .build();
        traineePlanRepository.save(traineePlan);

        log.info("✅ Quick test data created - Trainee ID: {}", trainee.getId());
    }

    private void insertTestData() {
        // 1. إنشاء BodyFocus
        BodyFocus chest = createBodyFocus("Chest");
        BodyFocus back = createBodyFocus("Back");
        BodyFocus shoulders = createBodyFocus("Shoulders");
        BodyFocus biceps = createBodyFocus("Biceps");
        BodyFocus triceps = createBodyFocus("Triceps");
        BodyFocus legs = createBodyFocus("Legs");

        // 2. إنشاء Exercises
        Exercise pushUps = createExercise("Push Ups", "Classic push up exercise",
                "3x15", 3, 30, 5, 15,
                Set.of(chest, triceps, shoulders));

        Exercise pullUps = createExercise("Pull Ups", "Upper body pulling exercise",
                "3x10", 3, 45, 8, 24,
                Set.of(back, biceps));

        Exercise squats = createExercise("Squats", "Lower body compound exercise",
                "3x20", 3, 60, 6, 18,
                Set.of(legs));

        Exercise benchPress = createExercise("Bench Press", "Chest pressing exercise",
                "3x12", 3, 45, 10, 30,
                Set.of(chest, triceps, shoulders));

        // 3. إنشاء WorkoutSessions
        WorkoutSessions upperBodySession = createWorkoutSession(
                "Upper Body Strength",
                "Focus on upper body muscles",
                TrainingLevel.BEGINNER,
                List.of(pushUps, pullUps, benchPress)
        );

        WorkoutSessions lowerBodySession = createWorkoutSession(
                "Lower Body Power",
                "Focus on leg muscles",
                TrainingLevel.BEGINNER,
                List.of(squats)
        );

        WorkoutSessions fullBodySession = createWorkoutSession(
                "Full Body Workout",
                "Complete body workout",
                TrainingLevel.INTERMEDIATE,
                List.of(pushUps, squats, pullUps)
        );

        // 4. إنشاء Plan
        Plans beginnerPlan = createPlan(
                "Beginner Strength Plan",
                "Build basic strength and muscle",
                TrainingLevel.BEGINNER,
                "3 days per week - Upper/Lower/Full body split"
        );

        // 5. إنشاء PlanDays
        PlanDays day1 = createPlanDay(1, "Focus on proper form", beginnerPlan,
                List.of(upperBodySession));
        PlanDays day2 = createPlanDay(2, "Control the movement", beginnerPlan,
                List.of(lowerBodySession));
        PlanDays day3 = createPlanDay(3, "Full body integration", beginnerPlan,
                List.of(fullBodySession));

        // ربط الـ PlanDays بالـ Plan
        beginnerPlan.getPlanDays().addAll(List.of(day1, day2, day3));
        plansRepository.save(beginnerPlan);

        // 6. إنشاء Trainee (إذا محتاج)
        Trainee testTrainee = createTestTrainee();

        // 7. إنشاء TraineePlan
        TraineePlan traineePlan = createTraineePlan(testTrainee, beginnerPlan, LocalDate.now());

        log.info("✅ Test data created successfully!");
        log.info("📊 Plan ID: {}, Trainee ID: {}", beginnerPlan.getId(), testTrainee.getId());
    }


}
