package com.abdoatiia542.GraduationProject;

import com.abdoatiia542.GraduationProject.model.Trainee;
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

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            log.info("⚙️ Command line runner started");

            insertUser();
            //Exercises Model
            insertPlan1Data(plansRepository, planDaysRepository, sessionRepository, exerciseRepository, bodyFocusRepository);
            insertPlan2Data(plansRepository, planDaysRepository, sessionRepository, exerciseRepository, bodyFocusRepository);
            insertPlan3Data(plansRepository, planDaysRepository, sessionRepository, exerciseRepository, bodyFocusRepository);
            insertPlan4Data(plansRepository, planDaysRepository, sessionRepository, exerciseRepository, bodyFocusRepository);
            insertPlan5Data(plansRepository, planDaysRepository, sessionRepository, exerciseRepository, bodyFocusRepository);
            insertPlan6Data(plansRepository, planDaysRepository, sessionRepository, exerciseRepository, bodyFocusRepository);
            insertPlan7Data(plansRepository, planDaysRepository, sessionRepository, exerciseRepository, bodyFocusRepository);
            insertPlan8Data(plansRepository, planDaysRepository, sessionRepository, exerciseRepository, bodyFocusRepository);

//            insertQuickTestData(); // نسخة مبسطة للتست السريع

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

            Trainee trainee2 = Trainee.builder()
                    .username("amer")
                    .email("amer@example.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.TRAINEE)
                    .gender(Gender.FEMALE)
                    .activityLevel(ActivityLevel.ACTIVE)
                    .goal(Goal.LOSE_WEIGHT)
                    .birthYear(2002)
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


    private void insertPlan1Data(PlansRepository plansRepo, PlanDaysRepository planDaysRepo, WorkoutSessionsRepository sessionRepo, ExerciseRepository exerciseRepo, BodyFocusRepository bodyFocusRepo) {
        // ✅ BodyFocus - Ensure they are managed entities
        BodyFocus legAndButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);
        BodyFocus cardio = getOrCreateBodyFocus("Cardio", bodyFocusRepo);
        BodyFocus chestMuscles = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus armShoulder = getOrCreateBodyFocus("Arm & Shoulder", bodyFocusRepo);
        BodyFocus sixPack = getOrCreateBodyFocus("Six Pack", bodyFocusRepo);
        BodyFocus backMuscles = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);

        // ✅ Exercises - Full Body A
        Exercise ex1 = buildExercise("Bodyweight Squat", "Squat down keeping your heels on the ground.", 2, "8-10", 60, 35, 70, null, null);
        ex1.setBodyFocuses(Set.of(legAndButt, cardio));
        ex1 = exerciseRepo.save(ex1);

        Exercise ex2 = buildExercise("Incline Push Up", "Push up with hands elevated for beginners.", 2, "AMRAP", 60, 25, 50, null, null);
        ex2.setBodyFocuses(Set.of(chestMuscles, armShoulder));
        ex2 = exerciseRepo.save(ex2);

        Exercise ex3 = buildExercise("Hand Plank", "Hold a straight plank from hands.", 2, "20-30s hold", 60, 20, 40, null, 30);
        ex3.setBodyFocuses(Set.of(sixPack, armShoulder));
        ex3 = exerciseRepo.save(ex3);

        WorkoutSessions fullBodyA = sessionRepo.save(WorkoutSessions.builder().name("Full Body A").image("https://example.com/images/full_body_a.png").trainingLevel(TrainingLevel.BEGINNER).exercises(List.of(ex1, ex2, ex3)).build());

        // ✅ Exercises - Full Body B
        Exercise ex4 = buildExercise("Inverted Row", "Pull your chest to a bar/table.", 2, "AMRAP", 60, 28, 56, null, null);
        ex4.setBodyFocuses(Set.of(backMuscles, armShoulder));
        ex4 = exerciseRepo.save(ex4);

        Exercise ex5 = buildExercise("Supermans", "Lift arms and legs while laying on stomach.", 2, "10-12", 60, 26, 52, null, null);
        ex5.setBodyFocuses(Set.of(backMuscles, legAndButt));
        ex5 = exerciseRepo.save(ex5);

        WorkoutSessions fullBodyB = sessionRepo.save(WorkoutSessions.builder().name("Full Body B").image("https://example.com/images/full_body_b.png").trainingLevel(TrainingLevel.BEGINNER).exercises(List.of(ex4, ex5)).build());

        // ✅ Plan & Days
        Plans plan = Plans.builder().name("Beginner BMI Level 1").goal("Build foundation, gain muscle").trainingLevel(TrainingLevel.BEGINNER).trainingSplit("Full Body").build();

        List<PlanDays> days = List.of(PlanDays.builder().dayNumber(1).plan(plan).workoutSessions(List.of(fullBodyA)).build(), PlanDays.builder().dayNumber(2).plan(plan).note("Rest or Light Activity").build(), PlanDays.builder().dayNumber(3).plan(plan).workoutSessions(List.of(fullBodyB)).build(), PlanDays.builder().dayNumber(4).plan(plan).note("Rest").build());

        plan.setPlanDays(days);
        plansRepo.save(plan);
    }

    private void insertPlan2Data(PlansRepository plansRepo, PlanDaysRepository planDaysRepo, WorkoutSessionsRepository sessionRepo, ExerciseRepository exerciseRepo, BodyFocusRepository bodyFocusRepo) {
        BodyFocus legButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);
        BodyFocus sixPack = getOrCreateBodyFocus("Six Pack", bodyFocusRepo);
        BodyFocus armShoulder = getOrCreateBodyFocus("Arm & Shoulder", bodyFocusRepo);

        // ✅ Full Body A
        List<Exercise> fullBodyAExercises = List.of(buildExercise("Dumbbell Goblet Squat", "Use appropriate weight", 2, "8-10", 60, 40, 80, Set.of(legButt), null), buildExercise("Dumbbell Bench Press", "Chest activation", 2, "8-10", 60, 30, 60, Set.of(chest), null), buildExercise("Dumbbell Row Unilateral", "Stable core, avoid twisting", 2, "8-10 per side", 60, 32, 64, Set.of(back), null), buildExercise("Bodyweight Assisted Pull Up", "Use assistance as needed", 2, "AMRAP", 60, 30, 60, Set.of(back), null), buildExercise("Hand Plank", "Straight line, core engaged", 2, "30-45s hold", 60, 22, 44, Set.of(sixPack), 45), buildExercise("Glute Bridge", "Squeeze glutes", 2, "12-15", 60, 30, 60, Set.of(legButt), null)).stream().map(exerciseRepo::save).toList();

        WorkoutSessions fullBodyA = sessionRepo.save(WorkoutSessions.builder().name("Full Body A").trainingLevel(TrainingLevel.BEGINNER).exercises(fullBodyAExercises).build());

        // ✅ Full Body B
        List<Exercise> fullBodyBExercises = List.of(buildExercise("Dumbbell Romanian Deadlift", "Hinge at hips, straight back", 2, "10-12", 60, 38, 76, Set.of(legButt), null), buildExercise("Dumbbell Overhead Press", "Control weight", 2, "8-10", 60, 30, 60, Set.of(armShoulder), null), buildExercise("Inverted Row", "Choose appropriate variation", 2, "AMRAP", 60, 28, 56, Set.of(back), null), buildExercise("Elbow Side Plank", "Straight line, core engaged", 2, "30-45s hold per side", 60, 24, 48, Set.of(sixPack), 45), buildExercise("Bird Dog", "Engage core, balance", 2, "10-12 per side", 60, 25, 50, Set.of(sixPack), null), buildExercise("Dumbbell Curl", "Controlled movements", 2, "10-12", 60, 26, 52, Set.of(armShoulder), null)).stream().map(exerciseRepo::save).toList();

        WorkoutSessions fullBodyB = sessionRepo.save(WorkoutSessions.builder().name("Full Body B").trainingLevel(TrainingLevel.BEGINNER).exercises(fullBodyBExercises).build());

        // ✅ Full Body A - Repeat
        WorkoutSessions fullBodyARepeat = sessionRepo.save(WorkoutSessions.builder().name("Full Body A - Repeat").trainingLevel(TrainingLevel.BEGINNER).exercises(fullBodyAExercises).build());

        // ✅ Plan
        Plans plan = Plans.builder().name("Beginner BMI Level 2").goal("Build strength & muscle, increase fitness").trainingLevel(TrainingLevel.BEGINNER).trainingSplit("Full Body").build();

        List<PlanDays> days = List.of(PlanDays.builder().dayNumber(1).plan(plan).workoutSessions(List.of(fullBodyA)).build(), PlanDays.builder().dayNumber(2).plan(plan).note("Rest or Light Activity (yoga, walking, stretching)").build(), PlanDays.builder().dayNumber(3).plan(plan).workoutSessions(List.of(fullBodyB)).build(), PlanDays.builder().dayNumber(4).plan(plan).note("Rest or Light Activity").build(), PlanDays.builder().dayNumber(5).plan(plan).workoutSessions(List.of(fullBodyARepeat)).build(), PlanDays.builder().dayNumber(6).plan(plan).note("Rest").build(), PlanDays.builder().dayNumber(7).plan(plan).note("Rest").build());

        plan.setPlanDays(days);
        plansRepo.save(plan);
    }

    private void insertPlan3Data(PlansRepository plansRepo, PlanDaysRepository planDaysRepo, WorkoutSessionsRepository sessionRepo, ExerciseRepository exerciseRepo, BodyFocusRepository bodyFocusRepo) {
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);
        BodyFocus armShoulder = getOrCreateBodyFocus("Arm & Shoulder", bodyFocusRepo);
        BodyFocus legButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);

        // ✅ Upper Body A
        List<Exercise> upperAExercises = List.of(buildExercise("Barbell Reverse Grip Bench Press", "Start with empty bar, gradual increase", 2, "8-10", 90, 35, 70, Set.of(chest), null), buildExercise("Dumbbell Row Bilateral", "Stable core, flat back", 2, "8-10 per side", 90, 32, 64, Set.of(back), null), buildExercise("Dumbbell Overhead Press", "Control weight, gradual increase", 2, "8-10", 90, 30, 60, Set.of(armShoulder), null), buildExercise("Bodyweight Assisted Pull Up", "Decrease assistance/increase weight", 2, "AMRAP", 90, 30, 60, Set.of(back), null), buildExercise("Dumbbell Curl", "Controlled movements", 2, "10-12", 60, 26, 52, Set.of(armShoulder), null), buildExercise("Dumbbell Seated Overhead Tricep Extension", "Elbows close to head", 2, "10-12", 60, 27, 54, Set.of(armShoulder), null)).stream().map(exerciseRepo::save).toList();

        WorkoutSessions upperBodyA = sessionRepo.save(WorkoutSessions.builder().name("Upper Body A").trainingLevel(TrainingLevel.INTERMEDIATE).exercises(upperAExercises).build());

        // ✅ Lower Body A
        List<Exercise> lowerAExercises = List.of(buildExercise("Barbell Squat", "Start with empty bar, gradual increase", 2, "8-10", 90, 38, 76, Set.of(legButt), null), buildExercise("Dumbbell Romanian Deadlift", "Hinge at hips, straight back", 2, "10-12", 90, 36, 72, Set.of(legButt), null), buildExercise("Machine Leg Press", "Control weight, don't lock knees", 2, "10-12", 60, 34, 68, Set.of(legButt), null), buildExercise("Machine Leg Extension", "Controlled, no momentum", 2, "12-15", 60, 30, 60, Set.of(legButt), null), buildExercise("Machine Seated Leg Curl", "Squeeze hamstrings", 2, "12-15", 60, 30, 60, Set.of(legButt), null), buildExercise("Machine Standing Calf Raises", "", 2, "15-20", 60, 28, 56, Set.of(legButt), null)).stream().map(exerciseRepo::save).toList();

        WorkoutSessions lowerBodyA = sessionRepo.save(WorkoutSessions.builder().name("Lower Body A").trainingLevel(TrainingLevel.INTERMEDIATE).exercises(lowerAExercises).build());

        // ✅ Upper Body B
        List<Exercise> upperBExercises = List.of(buildExercise("Dumbbell Bench Press", "Muscle-mind connection", 2, "12-15", 60, 30, 60, Set.of(chest), null), buildExercise("Dumbbell Row Bilateral", "Lighter than Day 1", 2, "12-15", 60, 30, 60, Set.of(back), null), buildExercise("Dumbbell Lateral Raise", "Shoulder stability", 2, "12-15", 60, 28, 56, Set.of(armShoulder), null), buildExercise("Cable Rope Face Pulls", "Light weight, rear deltoids", 2, "15-20", 60, 26, 52, Set.of(armShoulder), null), buildExercise("Dumbbell Curl", "", 2, "12-15", 60, 26, 52, Set.of(armShoulder), null), buildExercise("Dumbbell Overhead Tricep Extension", "", 2, "12-15", 60, 27, 54, Set.of(armShoulder), null)).stream().map(exerciseRepo::save).toList();

        WorkoutSessions upperBodyB = sessionRepo.save(WorkoutSessions.builder().name("Upper Body B").trainingLevel(TrainingLevel.INTERMEDIATE).exercises(upperBExercises).build());

        // ✅ Lower Body B
        List<Exercise> lowerBExercises = List.of(buildExercise("Walking Lunge", "Balance and control", 2, "10-12 per leg", 60, 35, 70, Set.of(legButt), null), buildExercise("Glute Bridge", "Glute activation", 2, "15-20", 60, 30, 60, Set.of(legButt), null), buildExercise("Machine Seated Leg Curl", "Squeeze hamstrings", 2, "15-20", 60, 30, 60, Set.of(legButt), null), buildExercise("Machine Leg Extension", "Controlled movements", 2, "15-20", 60, 30, 60, Set.of(legButt), null), buildExercise("Machine Standing Calf Raises", "", 2, "20-25", 60, 28, 56, Set.of(legButt), null)).stream().map(exerciseRepo::save).toList();

        WorkoutSessions lowerBodyB = sessionRepo.save(WorkoutSessions.builder().name("Lower Body B").trainingLevel(TrainingLevel.INTERMEDIATE).exercises(lowerBExercises).build());

        // ✅ Plan & Days
        Plans plan = Plans.builder().name("BMI Level 3").goal("Increase strength & muscle, improve fitness").trainingLevel(TrainingLevel.INTERMEDIATE).trainingSplit("Upper/Lower").build();

        List<PlanDays> days = List.of(PlanDays.builder().dayNumber(1).plan(plan).workoutSessions(List.of(upperBodyA)).build(), PlanDays.builder().dayNumber(2).plan(plan).workoutSessions(List.of(lowerBodyA)).build(), PlanDays.builder().dayNumber(3).plan(plan).note("Rest or Active Recovery").build(), PlanDays.builder().dayNumber(4).plan(plan).workoutSessions(List.of(upperBodyB)).build(), PlanDays.builder().dayNumber(5).plan(plan).workoutSessions(List.of(lowerBodyB)).build(), PlanDays.builder().dayNumber(6).plan(plan).note("Rest").build(), PlanDays.builder().dayNumber(7).plan(plan).note("Rest").build());

        plan.setPlanDays(days);
        plansRepo.save(plan);
    }

    private void insertPlan4Data(PlansRepository plansRepo, PlanDaysRepository planDaysRepo, WorkoutSessionsRepository sessionRepo, ExerciseRepository exerciseRepo, BodyFocusRepository bodyFocusRepo) {
        // ✅ Body Focuses
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);
        BodyFocus armShoulder = getOrCreateBodyFocus("Arm & Shoulder", bodyFocusRepo);
        BodyFocus legButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);

        // ✅ Upper Body A
        List<Exercise> upperAExercises = List.of(buildExercise("Barbell Reverse Grip Bench Press", "Controlled movements, full ROM", 3, "8-12", 90, 35, 105, Set.of(chest), null), buildExercise("Dumbbell Row Bilateral", "Stable core", 3, "8-12", 90, 32, 96, Set.of(back), null), buildExercise("Dumbbell Overhead Press", "Control weight", 3, "8-12", 90, 30, 90, Set.of(armShoulder), null), buildExercise("Chin Ups", "Or Lat Pulldowns (918)", 3, "AMRAP", 90, 34, 102, Set.of(back, armShoulder), null), buildExercise("Dumbbell Curl", "Control weight", 3, "10-15", 60, 26, 78, Set.of(armShoulder), null), buildExercise("Cable Rope Pushdown", "Elbows close to body", 3, "12-15", 60, 27, 81, Set.of(armShoulder), null)).stream().map(exerciseRepo::save).toList();

        WorkoutSessions upperBodyA = sessionRepo.save(WorkoutSessions.builder().name("Upper Body A").trainingLevel(TrainingLevel.INTERMEDIATE).exercises(upperAExercises).build());

        // ✅ Lower Body A
        List<Exercise> lowerAExercises = List.of(buildExercise("Barbell Squat", "Proper form, deep as comfortable", 3, "8-12", 120, 38, 114, Set.of(legButt), null), buildExercise("Dumbbell Romanian Deadlift", "Hinge at hips, straight back", 3, "10-15", 120, 36, 108, Set.of(legButt, back), null), buildExercise("Machine Leg Press", "Control weight", 3, "10-15", 90, 34, 102, Set.of(legButt), null), buildExercise("Machine Leg Extension", "Control movement", 3, "12-15", 60, 30, 90, Set.of(legButt), null), buildExercise("Machine Seated Leg Curl", "Squeeze hamstrings", 3, "12-15", 60, 30, 90, Set.of(legButt), null), buildExercise("Dumbbell Calf Raise", "Full ROM", 3, "15-20", 60, 28, 84, Set.of(legButt), null)).stream().map(exerciseRepo::save).toList();

        WorkoutSessions lowerBodyA = sessionRepo.save(WorkoutSessions.builder().name("Lower Body A").trainingLevel(TrainingLevel.INTERMEDIATE).exercises(lowerAExercises).build());

        // ✅ Upper Body B
        List<Exercise> upperBExercises = List.of(buildExercise("Dumbbell Bench Press", "Lighter than Day 1", 3, "12-15", 75, 30, 90, Set.of(chest), null), buildExercise("Dumbbell Row Bilateral", "Lighter than Day 1", 3, "12-15", 75, 30, 90, Set.of(back), null), buildExercise("Dumbbell Arnold Press", "Controlled movements", 3, "10-15", 75, 30, 90, Set.of(armShoulder), null), buildExercise("Cable Rope Face Pulls", "Light weight, rear deltoids", 3, "15-20", 45, 26, 78, Set.of(armShoulder, back), null), buildExercise("Dumbbell Curl", "Lighter than Day 1", 2, "15-20", 45, 26, 52, Set.of(armShoulder), null), buildExercise("Dumbbell Overhead Tricep Extension", "Lighter than Day 1", 2, "15-20", 45, 27, 54, Set.of(armShoulder), null)).stream().map(exerciseRepo::save).toList();

        WorkoutSessions upperBodyB = sessionRepo.save(WorkoutSessions.builder().name("Upper Body B").trainingLevel(TrainingLevel.INTERMEDIATE).exercises(upperBExercises).build());

        // ✅ Lower Body B
        List<Exercise> lowerBExercises = List.of(buildExercise("Walking Lunge", "Balance and control", 3, "10-12 per leg", 75, 35, 105, Set.of(legButt), null), buildExercise("Glute Bridge", "Squeeze glutes", 3, "15-20", 75, 30, 90, Set.of(legButt), null), buildExercise("Machine Seated Leg Curl", "Squeeze hamstrings", 3, "15-20", 45, 30, 90, Set.of(legButt), null), buildExercise("Machine Leg Extension", "Controlled movements", 3, "15-20", 45, 30, 90, Set.of(legButt), null), buildExercise("Calf Raises", "Full ROM", 3, "20-25", 45, 28, 84, Set.of(legButt), null)).stream().map(exerciseRepo::save).toList();

        WorkoutSessions lowerBodyB = sessionRepo.save(WorkoutSessions.builder().name("Lower Body B").trainingLevel(TrainingLevel.INTERMEDIATE).exercises(lowerBExercises).build());

        // ✅ Plan & Days
        Plans plan = Plans.builder().name("BMI Level 4").goal("Balanced fitness (strength, hypertrophy, endurance)").trainingLevel(TrainingLevel.INTERMEDIATE).trainingSplit("Upper/Lower").build();

        List<PlanDays> days = List.of(PlanDays.builder().dayNumber(1).plan(plan).workoutSessions(List.of(upperBodyA)).build(), PlanDays.builder().dayNumber(2).plan(plan).workoutSessions(List.of(lowerBodyA)).build(), PlanDays.builder().dayNumber(3).plan(plan).note("Rest or Active Recovery (light cardio, stretching, yoga)").build(), PlanDays.builder().dayNumber(4).plan(plan).workoutSessions(List.of(upperBodyB)).build(), PlanDays.builder().dayNumber(5).plan(plan).workoutSessions(List.of(lowerBodyB)).build(), PlanDays.builder().dayNumber(6).plan(plan).note("Rest").build(), PlanDays.builder().dayNumber(7).plan(plan).note("Rest").build());

        plan.setPlanDays(days);
        plansRepo.save(plan);
    }

    private void insertPlan5Data(PlansRepository plansRepo, PlanDaysRepository planDaysRepo, WorkoutSessionsRepository sessionRepo, ExerciseRepository exerciseRepo, BodyFocusRepository bodyFocusRepo) {
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

        // ✅ Full Body A
        List<Exercise> fullBodyAExercises = List.of(buildExercise("Dumbbell Goblet Squat", "Or Barbell Squats (572)", 3, "10-12", 90, 38, 114, Set.of(leg, butt), null), buildExercise("Dumbbell Bench Press", "Focus on chest activation", 3, "10-12", 90, 35, 105, Set.of(chest), null), buildExercise("Dumbbell Row Bilateral", "Keep core engaged", 3, "10-12 per side", 90, 34, 102, Set.of(back), null), buildExercise("Dumbbell Overhead Press", "Dumbbells or barbells", 3, "10-12", 90, 30, 90, Set.of(arm, shoulder), null), buildExercise("Hand Plank", "Engage core", 3, "30-60s hold", 90, 24, 72, Set.of(sixPack, core), 60)).stream().map(exerciseRepo::save).toList();

        WorkoutSessions fullBodyA = sessionRepo.save(WorkoutSessions.builder().name("Full Body A + Cardio").trainingLevel(TrainingLevel.INTERMEDIATE).exercises(fullBodyAExercises).build());

        // ✅ Full Body B
        List<Exercise> fullBodyBExercises = List.of(buildExercise("Dumbbell Romanian Deadlift", "Hinge at hips, straight back", 3, "10-12", 90, 36, 108, Set.of(leg, butt), null), buildExercise("Dumbbell Reverse Lunge", "Maintain balance", 3, "10-12 per leg", 90, 36, 108, Set.of(leg, butt), null), buildExercise("Dumbbell Curl", "Controlled movement", 3, "12-15", 60, 26, 78, Set.of(arm, shoulder), null), buildExercise("Dumbbell Overhead Tricep Extension", "Elbows close to head", 3, "12-15", 60, 27, 81, Set.of(arm, shoulder), null), buildExercise("Bicycle Crunch", "Engage core", 3, "15-20 per side", 60, 28, 84, Set.of(sixPack, core), null)).stream().map(exerciseRepo::save).toList();

        WorkoutSessions fullBodyB = sessionRepo.save(WorkoutSessions.builder().name("Full Body B + Cardio").trainingLevel(TrainingLevel.INTERMEDIATE).exercises(fullBodyBExercises).build());

        // ✅ Full Body C
        List<Exercise> fullBodyCExercises = List.of(buildExercise("Bodyweight Squat", "", 2, "15", 30, 32, 64, Set.of(leg, butt), null), buildExercise("Push Up", "Or Incline Push-Ups", 2, "AMRAP", 30, 28, 56, Set.of(chest, arm, shoulder), null), buildExercise("Dumbbell Row Bilateral", "", 2, "10-12 per side", 30, 32, 64, Set.of(back), null), buildExercise("Cardio Jumping Jacks", "", 2, "20", 30, 30, 60, Set.of(cardio), null), buildExercise("Mountain Climber", "", 2, "15-20 per side", 30, 28, 56, Set.of(cardio, core), null)).stream().map(exerciseRepo::save).toList();

        WorkoutSessions fullBodyC = sessionRepo.save(WorkoutSessions.builder().name("Full Body C + Cardio").trainingLevel(TrainingLevel.INTERMEDIATE).exercises(fullBodyCExercises).build());

        // ✅ Plan & Days
        Plans plan = Plans.builder().name("BMI Level 5").goal("Improve cardio, build muscle, burn calories").trainingLevel(TrainingLevel.INTERMEDIATE).trainingSplit("Full Body with Cardio").build();

        List<PlanDays> days = List.of(PlanDays.builder().dayNumber(1).plan(plan).workoutSessions(List.of(fullBodyA)).build(), PlanDays.builder().dayNumber(2).plan(plan).note("Rest or Light Activity (walking, stretching)").build(), PlanDays.builder().dayNumber(3).plan(plan).workoutSessions(List.of(fullBodyB)).build(), PlanDays.builder().dayNumber(4).plan(plan).note("Rest or Light Activity").build(), PlanDays.builder().dayNumber(5).plan(plan).workoutSessions(List.of(fullBodyC)).build(), PlanDays.builder().dayNumber(6).plan(plan).note("Rest").build(), PlanDays.builder().dayNumber(7).plan(plan).note("Rest").build());

        plan.setPlanDays(days);
        plansRepo.save(plan);
    }

    private void insertPlan6Data(PlansRepository plansRepo, PlanDaysRepository planDaysRepo, WorkoutSessionsRepository sessionRepo, ExerciseRepository exerciseRepo, BodyFocusRepository bodyFocusRepo) {
        // ✅ BodyFocus
        BodyFocus legButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus sixPack = getOrCreateBodyFocus("Six Pack", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);

        // ✅ Full Body A
        List<Exercise> fullBodyA = List.of(buildExercise("Chair Pose", "Controlled movements", 2, "8-10", 60, 18, 36, Set.of(legButt), null), buildExercise("Incline Push Up", "Or Wall Push-Ups", 2, "AMRAP", 60, 20, 40, Set.of(chest), null), buildExercise("Adductor Stretch Seated Bilateral Static", "Resistance band or light dumbbells", 2, "10-12", 60, 12, 24, Set.of(legButt), null), buildExercise("Bird Dog", "Engage core, balance", 2, "8-10 per side", 60, 16, 32, Set.of(sixPack), null), buildExercise("Hand Plank", "From knees if needed", 2, "20-30s hold", 60, 14, 28, Set.of(sixPack), null)).stream().map(exerciseRepo::save).toList();

        WorkoutSessions sessionA = sessionRepo.save(WorkoutSessions.builder().name("Full Body A + Cardio").trainingLevel(TrainingLevel.BEGINNER).exercises(fullBodyA).build());

        // ✅ Full Body B
        List<Exercise> fullBodyB = List.of(buildExercise("Chair Pose", "", 2, "8-10", 60, 18, 36, Set.of(legButt), null), buildExercise("Incline Push Up", "Or Wall Push-Ups", 2, "AMRAP", 60, 20, 40, Set.of(chest), null), buildExercise("Band Pull Apart", "Squeeze shoulder blades", 2, "15-20", 60, 15, 30, Set.of(back), null), buildExercise("Bird Dog", "", 2, "8-10 per side", 60, 16, 32, Set.of(sixPack), null), buildExercise("Elbow Side Plank", "From knees if needed", 2, "20-30s hold per side", 60, 14, 28, Set.of(sixPack), null)).stream().map(exerciseRepo::save).toList();

        WorkoutSessions sessionB = sessionRepo.save(WorkoutSessions.builder().name("Full Body B + Cardio").trainingLevel(TrainingLevel.BEGINNER).exercises(fullBodyB).build());

        // ✅ Full Body C
        List<Exercise> fullBodyC = List.of(buildExercise("Chair Pose", "Controlled movements", 2, "8-10", 60, 18, 36, Set.of(legButt), null), buildExercise("Incline Push Up", "Or Wall Push-Ups", 2, "AMRAP", 60, 20, 40, Set.of(chest), null), buildExercise("Adductor Stretch Seated Bilateral Static", "Resistance band or light dumbbells", 2, "10-12", 60, 12, 24, Set.of(legButt), null), buildExercise("Bird Dog", "Engage core, balance", 2, "8-10 per side", 60, 16, 32, Set.of(sixPack), null), buildExercise("Hand Plank", "From knees if needed", 2, "20-30s hold", 60, 14, 28, Set.of(sixPack), null)).stream().map(exerciseRepo::save).toList();

        WorkoutSessions sessionC = sessionRepo.save(WorkoutSessions.builder().name("Full Body C + Cardio").trainingLevel(TrainingLevel.BEGINNER).exercises(fullBodyC).build());

        // ✅ Plan & Days
        Plans plan = Plans.builder().name("BMI Level 6").goal("Improve cardio, strength, mobility; build foundation").trainingLevel(TrainingLevel.BEGINNER).trainingSplit("Full Body with Cardio").build();

        List<PlanDays> days = List.of(PlanDays.builder().dayNumber(1).plan(plan).workoutSessions(List.of(sessionA)).build(), PlanDays.builder().dayNumber(2).plan(plan).note("Rest or Light Activity (gentle stretching, short walk)").build(), PlanDays.builder().dayNumber(3).plan(plan).workoutSessions(List.of(sessionB)).build(), PlanDays.builder().dayNumber(4).plan(plan).note("Rest or Light Activity").build(), PlanDays.builder().dayNumber(5).plan(plan).workoutSessions(List.of(sessionC)).build(), PlanDays.builder().dayNumber(6).plan(plan).note("Rest").build(), PlanDays.builder().dayNumber(7).plan(plan).note("Rest").build());

        plan.setPlanDays(days);
        plansRepo.save(plan);
    }

    private void insertPlan7Data(PlansRepository plansRepo, PlanDaysRepository planDaysRepo, WorkoutSessionsRepository sessionRepo, ExerciseRepository exerciseRepo, BodyFocusRepository bodyFocusRepo) {
        BodyFocus cardio = getOrCreateBodyFocus("Cardio", bodyFocusRepo);
        BodyFocus legButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus armShoulder = getOrCreateBodyFocus("Arm & Shoulder", bodyFocusRepo);
        BodyFocus sixPack = getOrCreateBodyFocus("Six Pack", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);

        // ✅ Full Body A
        List<Exercise> fullBodyAExercises = List.of(buildExercise("Cardio Quick Feet", "Seated Marches (light effort, mimicking marching motion)", 2, "10-15", 90, 18, 36, Set.of(cardio, legButt), null), buildExercise("Push Up", "Wall Push-Ups (or Incline Push-Ups if easier)", 2, "AMRAP", 90, 20, 40, Set.of(chest, armShoulder), null), buildExercise("Chair Pose", "Chair Stands (use arms for assistance)", 2, "5-8", 90, 16, 32, Set.of(legButt), null), buildExercise("Backward Arm Circle", "Overhead Arm Raises (seated or standing)", 2, "8-10", 60, 12, 24, Set.of(armShoulder), null), buildExercise("Dumbbell Seated Calf Raise", "Seated Bicep Curls (light weights or resistance band)", 1, "10-12", 60, 10, 10, Set.of(legButt), null), buildExercise("Dumbbell Overhead Tricep Extension", "Overhead Tricep Extensions (light weight or resistance band)", 1, "10-12", 60, 10, 10, Set.of(armShoulder), null)).stream().map(exerciseRepo::save).toList();

        WorkoutSessions fullBodyA = sessionRepo.save(WorkoutSessions.builder().name("Full Body A").trainingLevel(TrainingLevel.BEGINNER).exercises(fullBodyAExercises).build());

        // ✅ Mobility & Core
        List<Exercise> mobilityCore = List.of(buildExercise("Seated Forward Bend", "Modify by bending knees", 2, "30-60s hold", 60, 12, 24, Set.of(sixPack), 60), buildExercise("Seated Ulnar Nerve Slider", "Adapt along wall as seated wall slide", 2, "10-12", 60, 10, 20, Set.of(armShoulder), null), buildExercise("Bird Dog", "Modify on knees", 2, "5-8 per side", 60, 14, 28, Set.of(sixPack), null), buildExercise("Dumbbell Standing Hip Abduction", "Use chair for balance", 2, "10-12 per side", 60, 16, 32, Set.of(legButt), null), buildExercise("Treadmill Walk", "Gentle pace, flat surface", null, null, null, 30, 30, Set.of(cardio), 900)).stream().map(exerciseRepo::save).toList();

        WorkoutSessions mobilitySession = sessionRepo.save(WorkoutSessions.builder().name("Mobility & Core").trainingLevel(TrainingLevel.BEGINNER).exercises(mobilityCore).build());

        // ✅ Repeat Full Body A (same exercises)
        WorkoutSessions repeatFullBodyA = sessionRepo.save(WorkoutSessions.builder().name("Repeat Full Body A").trainingLevel(TrainingLevel.BEGINNER).exercises(fullBodyAExercises).build());

        // ✅ Plan & Days
        Plans plan = Plans.builder().name("Beginner BMI Level 7").goal("Improve mobility, increase activity, enhance cardio").trainingLevel(TrainingLevel.BEGINNER).trainingSplit("Full Body").build();

        List<PlanDays> days = List.of(PlanDays.builder().dayNumber(1).plan(plan).workoutSessions(List.of(fullBodyA)).build(), PlanDays.builder().dayNumber(2).plan(plan).note("Rest").build(), PlanDays.builder().dayNumber(3).plan(plan).workoutSessions(List.of(mobilitySession)).build(), PlanDays.builder().dayNumber(4).plan(plan).note("Rest").build(), PlanDays.builder().dayNumber(5).plan(plan).workoutSessions(List.of(repeatFullBodyA)).build(), PlanDays.builder().dayNumber(6).plan(plan).note("Rest").build(), PlanDays.builder().dayNumber(7).plan(plan).note("Rest").build());

        plan.setPlanDays(days);
        plansRepo.save(plan);
    }

    private void insertPlan8Data(PlansRepository plansRepo, PlanDaysRepository planDaysRepo, WorkoutSessionsRepository sessionRepo, ExerciseRepository exerciseRepo, BodyFocusRepository bodyFocusRepo) {
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);
        BodyFocus armShoulder = getOrCreateBodyFocus("Arm & Shoulder", bodyFocusRepo);
        BodyFocus legButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);
        BodyFocus sixPack = getOrCreateBodyFocus("Six Pack", bodyFocusRepo);
        BodyFocus cardio = getOrCreateBodyFocus("Cardio", bodyFocusRepo);

        // ✅ Push A
        List<Exercise> pushA = List.of(buildExercise("Barbell Bench Press", "Heavy set", 4, "6-8", 120, 42, 168, Set.of(chest), null), buildExercise("Standing Overhead Press", "Barbell or Dumbbells", 4, "8-10", 90, 36, 144, Set.of(armShoulder), null), buildExercise("Incline Dumbbell Press", "Upper chest focus", 3, "10-12", 90, 34, 102, Set.of(chest), null), buildExercise("Cable Lateral Raises", "Strict form", 3, "12-15", 60, 28, 84, Set.of(armShoulder), null), buildExercise("Tricep Dips", "Weighted if advanced", 3, "10-12", 90, 30, 90, Set.of(armShoulder), null)).stream().map(exerciseRepo::save).toList();
        WorkoutSessions pushSessionA = sessionRepo.save(WorkoutSessions.builder().name("Push A").trainingLevel(TrainingLevel.ADVANCED).exercises(pushA).build());

        // ✅ Pull A
        List<Exercise> pullA = List.of(buildExercise("Barbell Deadlift", "Heavy", 4, "5-6", 150, 50, 200, Set.of(back), null), buildExercise("Pull Ups", "Weighted if possible", 4, "AMRAP", 90, 34, 136, Set.of(back, armShoulder), null), buildExercise("Barbell Row", "Strict back control", 4, "8-10", 90, 36, 144, Set.of(back), null), buildExercise("Face Pulls", "Rear delt focus", 3, "15-20", 60, 28, 84, Set.of(back, armShoulder), null), buildExercise("Barbell Curl", "Heavy", 3, "10-12", 60, 30, 90, Set.of(armShoulder), null)).stream().map(exerciseRepo::save).toList();
        WorkoutSessions pullSessionA = sessionRepo.save(WorkoutSessions.builder().name("Pull A").trainingLevel(TrainingLevel.ADVANCED).exercises(pullA).build());

        // ✅ Legs A
        List<Exercise> legsA = List.of(buildExercise("Barbell Back Squat", "Heavy, below parallel", 4, "6-8", 150, 45, 180, Set.of(legButt), null), buildExercise("Romanian Deadlift", "Hamstring focus", 4, "8-10", 120, 40, 160, Set.of(legButt, back), null), buildExercise("Walking Lunges", "With Dumbbells", 3, "12 per leg", 90, 36, 108, Set.of(legButt), null), buildExercise("Calf Raise", "Slow & controlled", 4, "15-20", 60, 28, 112, Set.of(legButt), null)).stream().map(exerciseRepo::save).toList();
        WorkoutSessions legsSessionA = sessionRepo.save(WorkoutSessions.builder().name("Legs A").trainingLevel(TrainingLevel.ADVANCED).exercises(legsA).build());

        // ✅ Core & Cardio
        List<Exercise> coreCardio = List.of(buildExercise("Plank", "Hold tight core", 3, "60s hold", 60, 24, 72, Set.of(sixPack), 60), buildExercise("Bicycle Crunch", "Fast tempo", 3, "20 per side", 60, 26, 78, Set.of(sixPack), null), buildExercise("Mountain Climbers", "Fast pace", 3, "20 per side", 45, 28, 84, Set.of(cardio, sixPack), null), buildExercise("Burpees", "Explosive", 3, "15", 60, 35, 105, Set.of(cardio), null)).stream().map(exerciseRepo::save).toList();
        WorkoutSessions coreCardioSession = sessionRepo.save(WorkoutSessions.builder().name("Core & Cardio").trainingLevel(TrainingLevel.ADVANCED).exercises(coreCardio).build());

        // ✅ Push B
        List<Exercise> pushB = List.of(buildExercise("Dumbbell Shoulder Press", "Alternate arms", 4, "8-10", 90, 34, 136, Set.of(armShoulder), null), buildExercise("Flat Dumbbell Press", "Medium weight", 4, "10-12", 90, 35, 140, Set.of(chest), null), buildExercise("Overhead Tricep Extension", "Dumbbell", 3, "12-15", 60, 26, 78, Set.of(armShoulder), null), buildExercise("Chest Fly (Machine)", "Controlled", 3, "12-15", 60, 28, 84, Set.of(chest), null)).stream().map(exerciseRepo::save).toList();
        WorkoutSessions pushSessionB = sessionRepo.save(WorkoutSessions.builder().name("Push B").trainingLevel(TrainingLevel.ADVANCED).exercises(pushB).build());

        // ✅ Legs B
        List<Exercise> legsB = List.of(buildExercise("Front Squat", "Upright posture", 4, "8-10", 120, 42, 168, Set.of(legButt), null), buildExercise("Glute Bridge", "Barbell or Dumbbell", 3, "12-15", 90, 36, 108, Set.of(legButt), null), buildExercise("Leg Extension", "Machine", 3, "12-15", 60, 30, 90, Set.of(legButt), null), buildExercise("Calf Raise", "Single leg variation", 3, "15", 45, 28, 84, Set.of(legButt), null)).stream().map(exerciseRepo::save).toList();
        WorkoutSessions legsSessionB = sessionRepo.save(WorkoutSessions.builder().name("Legs B").trainingLevel(TrainingLevel.ADVANCED).exercises(legsB).build());

        Plans plan = Plans.builder().name("BMI Level 8").goal("Build advanced strength, hypertrophy & endurance").trainingSplit("Push / Pull / Legs + Core").trainingLevel(TrainingLevel.ADVANCED).build();

        List<PlanDays> days = List.of(PlanDays.builder().dayNumber(1).plan(plan).workoutSessions(List.of(pushSessionA)).build(), PlanDays.builder().dayNumber(2).plan(plan).workoutSessions(List.of(pullSessionA)).build(), PlanDays.builder().dayNumber(3).plan(plan).workoutSessions(List.of(legsSessionA)).build(), PlanDays.builder().dayNumber(4).plan(plan).workoutSessions(List.of(coreCardioSession)).build(), PlanDays.builder().dayNumber(5).plan(plan).workoutSessions(List.of(pushSessionB)).build(), PlanDays.builder().dayNumber(6).plan(plan).workoutSessions(List.of(legsSessionB)).build(), PlanDays.builder().dayNumber(7).plan(plan).note("Rest or Active Recovery").build());

        plan.setPlanDays(days);
        plansRepo.save(plan);
    }


    private BodyFocus getOrCreateBodyFocus(String name, BodyFocusRepository repo) {
        return repo.findByNameIgnoreCase(name).orElseGet(() -> repo.save(BodyFocus.builder().name(name).build()));
    }

    private Exercise buildExercise(String name, String desc, Integer sets, String reps, Integer rest, Integer cal, Integer totalCal, Set<BodyFocus> focuses, Integer duration) {
        return Exercise.builder().name(name).description(desc).sets(sets).reps(reps).durationRestSeconds(rest).caloriesBurned(cal).totalCalories(totalCal).durationSeconds(duration).bodyFocuses(focuses).build();
    }

//    new >>>

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
                                    int sets, int durationSeconds, int caloriesBurned,
                                    int restSeconds, Set<BodyFocus> bodyFocuses) {
        Exercise exercise = Exercise.builder()
                .name(name)
                .description(description)
                .reps(reps)
                .sets(sets)
                .durationSeconds(durationSeconds)
                .caloriesBurned(caloriesBurned)
                .totalCalories(sets * caloriesBurned)
                .durationRestSeconds(restSeconds)
                .bodyFocuses(bodyFocuses)
                .build();

        return exerciseRepository.save(exercise);
    }

    private WorkoutSessions createWorkoutSession(String name, String description,
                                                 TrainingLevel level, List<Exercise> exercises) {
        WorkoutSessions session = WorkoutSessions.builder()
                .name(name)
                .description(description)
                .trainingLevel(level)
                .exercises(exercises)
                .build();

        return sessionRepository.save(session);
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


}
