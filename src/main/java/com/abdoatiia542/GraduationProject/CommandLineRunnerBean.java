package com.abdoatiia542.GraduationProject;

import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.enumerations.Gender;
import com.abdoatiia542.GraduationProject.model.enumerations.Role;
import com.abdoatiia542.GraduationProject.model.enumerations.MealType;
import com.abdoatiia542.GraduationProject.model.enumerations.TrainingLevel;
import com.abdoatiia542.GraduationProject.model.food.FoodItem;
import com.abdoatiia542.GraduationProject.model.plan.*;
import com.abdoatiia542.GraduationProject.repository.food.FoodItemRepository;
import com.abdoatiia542.GraduationProject.repository.UserRepository;
import com.abdoatiia542.GraduationProject.repository.workouts.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandLineRunnerBean {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final FoodItemRepository foodItemRepository;
    private final PlansRepository plansRepository;
    private final PlanDaysRepository planDaysRepository;
    private final WorkoutSessionsRepository sessionRepository;
    private final ExerciseRepository exerciseRepository;
    private final BodyFocusRepository bodyFocusRepository;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            log.info("⚙️ Command line runner started");

            inserUser();
            insertFoodItems();
            //Exercises Model
            insertPlan1Data(plansRepository, planDaysRepository, sessionRepository, exerciseRepository, bodyFocusRepository);
            insertPlan2Data(plansRepository,planDaysRepository,sessionRepository,exerciseRepository,bodyFocusRepository);
            insertPlan3Data(plansRepository,planDaysRepository,sessionRepository,exerciseRepository,bodyFocusRepository);
            insertPlan4Data(plansRepository,planDaysRepository,sessionRepository,exerciseRepository,bodyFocusRepository);
            insertPlan5Data(plansRepository,planDaysRepository,sessionRepository,exerciseRepository,bodyFocusRepository);
            insertPlan6Data(plansRepository,planDaysRepository,sessionRepository,exerciseRepository,bodyFocusRepository);
            insertPlan7Data(plansRepository,planDaysRepository,sessionRepository,exerciseRepository,bodyFocusRepository);
            insertPlan8Data(plansRepository,planDaysRepository,sessionRepository,exerciseRepository,bodyFocusRepository);

            log.info("✅ CommandLineRunnerBean initialized successfully");
        };
    }
    private void insertPlan1Data(
            PlansRepository plansRepo,
            PlanDaysRepository planDaysRepo,
            WorkoutSessionsRepository sessionRepo,
            ExerciseRepository exerciseRepo,
            BodyFocusRepository bodyFocusRepo
    ) {
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

        WorkoutSessions fullBodyA = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Full Body A")
                        .image("https://example.com/images/full_body_a.png")
                        .trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(List.of(ex1, ex2, ex3))
                        .build()
        );

        // ✅ Exercises - Full Body B
        Exercise ex4 = buildExercise("Inverted Row", "Pull your chest to a bar/table.", 2, "AMRAP", 60, 28, 56, null, null);
        ex4.setBodyFocuses(Set.of(backMuscles, armShoulder));
        ex4 = exerciseRepo.save(ex4);

        Exercise ex5 = buildExercise("Supermans", "Lift arms and legs while laying on stomach.", 2, "10-12", 60, 26, 52, null, null);
        ex5.setBodyFocuses(Set.of(backMuscles, legAndButt));
        ex5 = exerciseRepo.save(ex5);

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

    private void insertPlan2Data(
            PlansRepository plansRepo,
            PlanDaysRepository planDaysRepo,
            WorkoutSessionsRepository sessionRepo,
            ExerciseRepository exerciseRepo,
            BodyFocusRepository bodyFocusRepo
    ) {
        BodyFocus legButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);
        BodyFocus sixPack = getOrCreateBodyFocus("Six Pack", bodyFocusRepo);
        BodyFocus armShoulder = getOrCreateBodyFocus("Arm & Shoulder", bodyFocusRepo);

        // ✅ Full Body A
        List<Exercise> fullBodyAExercises = List.of(
                buildExercise("Dumbbell Goblet Squat", "Use appropriate weight", 2, "8-10", 60, 40, 80, Set.of(legButt), null),
                buildExercise("Dumbbell Bench Press", "Chest activation", 2, "8-10", 60, 30, 60, Set.of(chest), null),
                buildExercise("Dumbbell Row Unilateral", "Stable core, avoid twisting", 2, "8-10 per side", 60, 32, 64, Set.of(back), null),
                buildExercise("Bodyweight Assisted Pull Up", "Use assistance as needed", 2, "AMRAP", 60, 30, 60, Set.of(back), null),
                buildExercise("Hand Plank", "Straight line, core engaged", 2, "30-45s hold", 60, 22, 44, Set.of(sixPack), 45),
                buildExercise("Glute Bridge", "Squeeze glutes", 2, "12-15", 60, 30, 60, Set.of(legButt), null)
        ).stream().map(exerciseRepo::save).toList();

        WorkoutSessions fullBodyA = sessionRepo.save(
                WorkoutSessions.builder().name("Full Body A").trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(fullBodyAExercises).build());

        // ✅ Full Body B
        List<Exercise> fullBodyBExercises = List.of(
                buildExercise("Dumbbell Romanian Deadlift", "Hinge at hips, straight back", 2, "10-12", 60, 38, 76, Set.of(legButt), null),
                buildExercise("Dumbbell Overhead Press", "Control weight", 2, "8-10", 60, 30, 60, Set.of(armShoulder), null),
                buildExercise("Inverted Row", "Choose appropriate variation", 2, "AMRAP", 60, 28, 56, Set.of(back), null),
                buildExercise("Elbow Side Plank", "Straight line, core engaged", 2, "30-45s hold per side", 60, 24, 48, Set.of(sixPack), 45),
                buildExercise("Bird Dog", "Engage core, balance", 2, "10-12 per side", 60, 25, 50, Set.of(sixPack), null),
                buildExercise("Dumbbell Curl", "Controlled movements", 2, "10-12", 60, 26, 52, Set.of(armShoulder), null)
        ).stream().map(exerciseRepo::save).toList();

        WorkoutSessions fullBodyB = sessionRepo.save(
                WorkoutSessions.builder().name("Full Body B").trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(fullBodyBExercises).build());

        // ✅ Full Body A - Repeat
        WorkoutSessions fullBodyARepeat = sessionRepo.save(
                WorkoutSessions.builder().name("Full Body A - Repeat").trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(fullBodyAExercises).build());

        // ✅ Plan
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
    private void insertPlan3Data(
            PlansRepository plansRepo,
            PlanDaysRepository planDaysRepo,
            WorkoutSessionsRepository sessionRepo,
            ExerciseRepository exerciseRepo,
            BodyFocusRepository bodyFocusRepo
    ) {
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);
        BodyFocus armShoulder = getOrCreateBodyFocus("Arm & Shoulder", bodyFocusRepo);
        BodyFocus legButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);

        // ✅ Upper Body A
        List<Exercise> upperAExercises = List.of(
                buildExercise("Barbell Reverse Grip Bench Press", "Start with empty bar, gradual increase", 2, "8-10", 90, 35, 70, Set.of(chest), null),
                buildExercise("Dumbbell Row Bilateral", "Stable core, flat back", 2, "8-10 per side", 90, 32, 64, Set.of(back), null),
                buildExercise("Dumbbell Overhead Press", "Control weight, gradual increase", 2, "8-10", 90, 30, 60, Set.of(armShoulder), null),
                buildExercise("Bodyweight Assisted Pull Up", "Decrease assistance/increase weight", 2, "AMRAP", 90, 30, 60, Set.of(back), null),
                buildExercise("Dumbbell Curl", "Controlled movements", 2, "10-12", 60, 26, 52, Set.of(armShoulder), null),
                buildExercise("Dumbbell Seated Overhead Tricep Extension", "Elbows close to head", 2, "10-12", 60, 27, 54, Set.of(armShoulder), null)
        ).stream().map(exerciseRepo::save).toList();

        WorkoutSessions upperBodyA = sessionRepo.save(
                WorkoutSessions.builder().name("Upper Body A").trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(upperAExercises).build());

        // ✅ Lower Body A
        List<Exercise> lowerAExercises = List.of(
                buildExercise("Barbell Squat", "Start with empty bar, gradual increase", 2, "8-10", 90, 38, 76, Set.of(legButt), null),
                buildExercise("Dumbbell Romanian Deadlift", "Hinge at hips, straight back", 2, "10-12", 90, 36, 72, Set.of(legButt), null),
                buildExercise("Machine Leg Press", "Control weight, don't lock knees", 2, "10-12", 60, 34, 68, Set.of(legButt), null),
                buildExercise("Machine Leg Extension", "Controlled, no momentum", 2, "12-15", 60, 30, 60, Set.of(legButt), null),
                buildExercise("Machine Seated Leg Curl", "Squeeze hamstrings", 2, "12-15", 60, 30, 60, Set.of(legButt), null),
                buildExercise("Machine Standing Calf Raises", "", 2, "15-20", 60, 28, 56, Set.of(legButt), null)
        ).stream().map(exerciseRepo::save).toList();

        WorkoutSessions lowerBodyA = sessionRepo.save(
                WorkoutSessions.builder().name("Lower Body A").trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(lowerAExercises).build());

        // ✅ Upper Body B
        List<Exercise> upperBExercises = List.of(
                buildExercise("Dumbbell Bench Press", "Muscle-mind connection", 2, "12-15", 60, 30, 60, Set.of(chest), null),
                buildExercise("Dumbbell Row Bilateral", "Lighter than Day 1", 2, "12-15", 60, 30, 60, Set.of(back), null),
                buildExercise("Dumbbell Lateral Raise", "Shoulder stability", 2, "12-15", 60, 28, 56, Set.of(armShoulder), null),
                buildExercise("Cable Rope Face Pulls", "Light weight, rear deltoids", 2, "15-20", 60, 26, 52, Set.of(armShoulder), null),
                buildExercise("Dumbbell Curl", "", 2, "12-15", 60, 26, 52, Set.of(armShoulder), null),
                buildExercise("Dumbbell Overhead Tricep Extension", "", 2, "12-15", 60, 27, 54, Set.of(armShoulder), null)
        ).stream().map(exerciseRepo::save).toList();

        WorkoutSessions upperBodyB = sessionRepo.save(
                WorkoutSessions.builder().name("Upper Body B").trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(upperBExercises).build());

        // ✅ Lower Body B
        List<Exercise> lowerBExercises = List.of(
                buildExercise("Walking Lunge", "Balance and control", 2, "10-12 per leg", 60, 35, 70, Set.of(legButt), null),
                buildExercise("Glute Bridge", "Glute activation", 2, "15-20", 60, 30, 60, Set.of(legButt), null),
                buildExercise("Machine Seated Leg Curl", "Squeeze hamstrings", 2, "15-20", 60, 30, 60, Set.of(legButt), null),
                buildExercise("Machine Leg Extension", "Controlled movements", 2, "15-20", 60, 30, 60, Set.of(legButt), null),
                buildExercise("Machine Standing Calf Raises", "", 2, "20-25", 60, 28, 56, Set.of(legButt), null)
        ).stream().map(exerciseRepo::save).toList();

        WorkoutSessions lowerBodyB = sessionRepo.save(
                WorkoutSessions.builder().name("Lower Body B").trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(lowerBExercises).build());

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
    private void insertPlan4Data(
            PlansRepository plansRepo,
            PlanDaysRepository planDaysRepo,
            WorkoutSessionsRepository sessionRepo,
            ExerciseRepository exerciseRepo,
            BodyFocusRepository bodyFocusRepo
    ) {
        // ✅ Body Focuses
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);
        BodyFocus armShoulder = getOrCreateBodyFocus("Arm & Shoulder", bodyFocusRepo);
        BodyFocus legButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);

        // ✅ Upper Body A
        List<Exercise> upperAExercises = List.of(
                buildExercise("Barbell Reverse Grip Bench Press", "Controlled movements, full ROM", 3, "8-12", 90, 35, 105, Set.of(chest), null),
                buildExercise("Dumbbell Row Bilateral", "Stable core", 3, "8-12", 90, 32, 96, Set.of(back), null),
                buildExercise("Dumbbell Overhead Press", "Control weight", 3, "8-12", 90, 30, 90, Set.of(armShoulder), null),
                buildExercise("Chin Ups", "Or Lat Pulldowns (918)", 3, "AMRAP", 90, 34, 102, Set.of(back, armShoulder), null),
                buildExercise("Dumbbell Curl", "Control weight", 3, "10-15", 60, 26, 78, Set.of(armShoulder), null),
                buildExercise("Cable Rope Pushdown", "Elbows close to body", 3, "12-15", 60, 27, 81, Set.of(armShoulder), null)
        ).stream().map(exerciseRepo::save).toList();

        WorkoutSessions upperBodyA = sessionRepo.save(
                WorkoutSessions.builder().name("Upper Body A").trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(upperAExercises).build());

        // ✅ Lower Body A
        List<Exercise> lowerAExercises = List.of(
                buildExercise("Barbell Squat", "Proper form, deep as comfortable", 3, "8-12", 120, 38, 114, Set.of(legButt), null),
                buildExercise("Dumbbell Romanian Deadlift", "Hinge at hips, straight back", 3, "10-15", 120, 36, 108, Set.of(legButt, back), null),
                buildExercise("Machine Leg Press", "Control weight", 3, "10-15", 90, 34, 102, Set.of(legButt), null),
                buildExercise("Machine Leg Extension", "Control movement", 3, "12-15", 60, 30, 90, Set.of(legButt), null),
                buildExercise("Machine Seated Leg Curl", "Squeeze hamstrings", 3, "12-15", 60, 30, 90, Set.of(legButt), null),
                buildExercise("Dumbbell Calf Raise", "Full ROM", 3, "15-20", 60, 28, 84, Set.of(legButt), null)
        ).stream().map(exerciseRepo::save).toList();

        WorkoutSessions lowerBodyA = sessionRepo.save(
                WorkoutSessions.builder().name("Lower Body A").trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(lowerAExercises).build());

        // ✅ Upper Body B
        List<Exercise> upperBExercises = List.of(
                buildExercise("Dumbbell Bench Press", "Lighter than Day 1", 3, "12-15", 75, 30, 90, Set.of(chest), null),
                buildExercise("Dumbbell Row Bilateral", "Lighter than Day 1", 3, "12-15", 75, 30, 90, Set.of(back), null),
                buildExercise("Dumbbell Arnold Press", "Controlled movements", 3, "10-15", 75, 30, 90, Set.of(armShoulder), null),
                buildExercise("Cable Rope Face Pulls", "Light weight, rear deltoids", 3, "15-20", 45, 26, 78, Set.of(armShoulder, back), null),
                buildExercise("Dumbbell Curl", "Lighter than Day 1", 2, "15-20", 45, 26, 52, Set.of(armShoulder), null),
                buildExercise("Dumbbell Overhead Tricep Extension", "Lighter than Day 1", 2, "15-20", 45, 27, 54, Set.of(armShoulder), null)
        ).stream().map(exerciseRepo::save).toList();

        WorkoutSessions upperBodyB = sessionRepo.save(
                WorkoutSessions.builder().name("Upper Body B").trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(upperBExercises).build());

        // ✅ Lower Body B
        List<Exercise> lowerBExercises = List.of(
                buildExercise("Walking Lunge", "Balance and control", 3, "10-12 per leg", 75, 35, 105, Set.of(legButt), null),
                buildExercise("Glute Bridge", "Squeeze glutes", 3, "15-20", 75, 30, 90, Set.of(legButt), null),
                buildExercise("Machine Seated Leg Curl", "Squeeze hamstrings", 3, "15-20", 45, 30, 90, Set.of(legButt), null),
                buildExercise("Machine Leg Extension", "Controlled movements", 3, "15-20", 45, 30, 90, Set.of(legButt), null),
                buildExercise("Calf Raises", "Full ROM", 3, "20-25", 45, 28, 84, Set.of(legButt), null)
        ).stream().map(exerciseRepo::save).toList();

        WorkoutSessions lowerBodyB = sessionRepo.save(
                WorkoutSessions.builder().name("Lower Body B").trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(lowerBExercises).build());

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
    private void insertPlan5Data(
            PlansRepository plansRepo,
            PlanDaysRepository planDaysRepo,
            WorkoutSessionsRepository sessionRepo,
            ExerciseRepository exerciseRepo,
            BodyFocusRepository bodyFocusRepo
    ) {
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
        List<Exercise> fullBodyAExercises = List.of(
                buildExercise("Dumbbell Goblet Squat", "Or Barbell Squats (572)", 3, "10-12", 90, 38, 114, Set.of(leg, butt), null),
                buildExercise("Dumbbell Bench Press", "Focus on chest activation", 3, "10-12", 90, 35, 105, Set.of(chest), null),
                buildExercise("Dumbbell Row Bilateral", "Keep core engaged", 3, "10-12 per side", 90, 34, 102, Set.of(back), null),
                buildExercise("Dumbbell Overhead Press", "Dumbbells or barbells", 3, "10-12", 90, 30, 90, Set.of(arm, shoulder), null),
                buildExercise("Hand Plank", "Engage core", 3, "30-60s hold", 90, 24, 72, Set.of(sixPack, core), 60)
        ).stream().map(exerciseRepo::save).toList();

        WorkoutSessions fullBodyA = sessionRepo.save(
                WorkoutSessions.builder().name("Full Body A + Cardio").trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(fullBodyAExercises).build());

        // ✅ Full Body B
        List<Exercise> fullBodyBExercises = List.of(
                buildExercise("Dumbbell Romanian Deadlift", "Hinge at hips, straight back", 3, "10-12", 90, 36, 108, Set.of(leg, butt), null),
                buildExercise("Dumbbell Reverse Lunge", "Maintain balance", 3, "10-12 per leg", 90, 36, 108, Set.of(leg, butt), null),
                buildExercise("Dumbbell Curl", "Controlled movement", 3, "12-15", 60, 26, 78, Set.of(arm, shoulder), null),
                buildExercise("Dumbbell Overhead Tricep Extension", "Elbows close to head", 3, "12-15", 60, 27, 81, Set.of(arm, shoulder), null),
                buildExercise("Bicycle Crunch", "Engage core", 3, "15-20 per side", 60, 28, 84, Set.of(sixPack, core), null)
        ).stream().map(exerciseRepo::save).toList();

        WorkoutSessions fullBodyB = sessionRepo.save(
                WorkoutSessions.builder().name("Full Body B + Cardio").trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(fullBodyBExercises).build());

        // ✅ Full Body C
        List<Exercise> fullBodyCExercises = List.of(
                buildExercise("Bodyweight Squat", "", 2, "15", 30, 32, 64, Set.of(leg, butt), null),
                buildExercise("Push Up", "Or Incline Push-Ups", 2, "AMRAP", 30, 28, 56, Set.of(chest, arm, shoulder), null),
                buildExercise("Dumbbell Row Bilateral", "", 2, "10-12 per side", 30, 32, 64, Set.of(back), null),
                buildExercise("Cardio Jumping Jacks", "", 2, "20", 30, 30, 60, Set.of(cardio), null),
                buildExercise("Mountain Climber", "", 2, "15-20 per side", 30, 28, 56, Set.of(cardio, core), null)
        ).stream().map(exerciseRepo::save).toList();

        WorkoutSessions fullBodyC = sessionRepo.save(
                WorkoutSessions.builder().name("Full Body C + Cardio").trainingLevel(TrainingLevel.INTERMEDIATE)
                        .exercises(fullBodyCExercises).build());

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

    private void insertPlan6Data(
            PlansRepository plansRepo,
            PlanDaysRepository planDaysRepo,
            WorkoutSessionsRepository sessionRepo,
            ExerciseRepository exerciseRepo,
            BodyFocusRepository bodyFocusRepo
    ) {
        // ✅ BodyFocus
        BodyFocus legButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus sixPack = getOrCreateBodyFocus("Six Pack", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);

        // ✅ Full Body A
        List<Exercise> fullBodyA = List.of(
                buildExercise("Chair Pose", "Controlled movements", 2, "8-10", 60, 18, 36, Set.of(legButt), null),
                buildExercise("Incline Push Up", "Or Wall Push-Ups", 2, "AMRAP", 60, 20, 40, Set.of(chest), null),
                buildExercise("Adductor Stretch Seated Bilateral Static", "Resistance band or light dumbbells", 2, "10-12", 60, 12, 24, Set.of(legButt), null),
                buildExercise("Bird Dog", "Engage core, balance", 2, "8-10 per side", 60, 16, 32, Set.of(sixPack), null),
                buildExercise("Hand Plank", "From knees if needed", 2, "20-30s hold", 60, 14, 28, Set.of(sixPack), null)
        ).stream().map(exerciseRepo::save).toList();

        WorkoutSessions sessionA = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Full Body A + Cardio")
                        .trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(fullBodyA)
                        .build()
        );

        // ✅ Full Body B
        List<Exercise> fullBodyB = List.of(
                buildExercise("Chair Pose", "", 2, "8-10", 60, 18, 36, Set.of(legButt), null),
                buildExercise("Incline Push Up", "Or Wall Push-Ups", 2, "AMRAP", 60, 20, 40, Set.of(chest), null),
                buildExercise("Band Pull Apart", "Squeeze shoulder blades", 2, "15-20", 60, 15, 30, Set.of(back), null),
                buildExercise("Bird Dog", "", 2, "8-10 per side", 60, 16, 32, Set.of(sixPack), null),
                buildExercise("Elbow Side Plank", "From knees if needed", 2, "20-30s hold per side", 60, 14, 28, Set.of(sixPack), null)
        ).stream().map(exerciseRepo::save).toList();

        WorkoutSessions sessionB = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Full Body B + Cardio")
                        .trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(fullBodyB)
                        .build()
        );

        // ✅ Full Body C
        List<Exercise> fullBodyC = List.of(
                buildExercise("Chair Pose", "Controlled movements", 2, "8-10", 60, 18, 36, Set.of(legButt), null),
                buildExercise("Incline Push Up", "Or Wall Push-Ups", 2, "AMRAP", 60, 20, 40, Set.of(chest), null),
                buildExercise("Adductor Stretch Seated Bilateral Static", "Resistance band or light dumbbells", 2, "10-12", 60, 12, 24, Set.of(legButt), null),
                buildExercise("Bird Dog", "Engage core, balance", 2, "8-10 per side", 60, 16, 32, Set.of(sixPack), null),
                buildExercise("Hand Plank", "From knees if needed", 2, "20-30s hold", 60, 14, 28, Set.of(sixPack), null)
        ).stream().map(exerciseRepo::save).toList();

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
    private void insertPlan7Data(
            PlansRepository plansRepo,
            PlanDaysRepository planDaysRepo,
            WorkoutSessionsRepository sessionRepo,
            ExerciseRepository exerciseRepo,
            BodyFocusRepository bodyFocusRepo
    ) {
        BodyFocus cardio = getOrCreateBodyFocus("Cardio", bodyFocusRepo);
        BodyFocus legButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus armShoulder = getOrCreateBodyFocus("Arm & Shoulder", bodyFocusRepo);
        BodyFocus sixPack = getOrCreateBodyFocus("Six Pack", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);

        // ✅ Full Body A
        List<Exercise> fullBodyAExercises = List.of(
                buildExercise("Cardio Quick Feet", "Seated Marches (light effort, mimicking marching motion)", 2, "10-15", 90, 18, 36, Set.of(cardio, legButt), null),
                buildExercise("Push Up", "Wall Push-Ups (or Incline Push-Ups if easier)", 2, "AMRAP", 90, 20, 40, Set.of(chest, armShoulder), null),
                buildExercise("Chair Pose", "Chair Stands (use arms for assistance)", 2, "5-8", 90, 16, 32, Set.of(legButt), null),
                buildExercise("Backward Arm Circle", "Overhead Arm Raises (seated or standing)", 2, "8-10", 60, 12, 24, Set.of(armShoulder), null),
                buildExercise("Dumbbell Seated Calf Raise", "Seated Bicep Curls (light weights or resistance band)", 1, "10-12", 60, 10, 10, Set.of(legButt), null),
                buildExercise("Dumbbell Overhead Tricep Extension", "Overhead Tricep Extensions (light weight or resistance band)", 1, "10-12", 60, 10, 10, Set.of(armShoulder), null)
        ).stream().map(exerciseRepo::save).toList();

        WorkoutSessions fullBodyA = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Full Body A")
                        .trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(fullBodyAExercises)
                        .build());

        // ✅ Mobility & Core
        List<Exercise> mobilityCore = List.of(
                buildExercise("Seated Forward Bend", "Modify by bending knees", 2, "30-60s hold", 60, 12, 24, Set.of(sixPack), 60),
                buildExercise("Seated Ulnar Nerve Slider", "Adapt along wall as seated wall slide", 2, "10-12", 60, 10, 20, Set.of(armShoulder), null),
                buildExercise("Bird Dog", "Modify on knees", 2, "5-8 per side", 60, 14, 28, Set.of(sixPack), null),
                buildExercise("Dumbbell Standing Hip Abduction", "Use chair for balance", 2, "10-12 per side", 60, 16, 32, Set.of(legButt), null),
                buildExercise("Treadmill Walk", "Gentle pace, flat surface", null, null, null, 30, 30, Set.of(cardio), 900)
        ).stream().map(exerciseRepo::save).toList();

        WorkoutSessions mobilitySession = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Mobility & Core")
                        .trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(mobilityCore)
                        .build());

        // ✅ Repeat Full Body A (same exercises)
        WorkoutSessions repeatFullBodyA = sessionRepo.save(
                WorkoutSessions.builder()
                        .name("Repeat Full Body A")
                        .trainingLevel(TrainingLevel.BEGINNER)
                        .exercises(fullBodyAExercises)
                        .build());

        // ✅ Plan & Days
        Plans plan = Plans.builder()
                .name("Beginner BMI Level 7")
                .goal("Improve mobility, increase activity, enhance cardio")
                .trainingLevel(TrainingLevel.BEGINNER)
                .trainingSplit("Full Body")
                .build();

        List<PlanDays> days = List.of(
                PlanDays.builder().dayNumber(1).plan(plan).workoutSessions(List.of(fullBodyA)).build(),
                PlanDays.builder().dayNumber(2).plan(plan).note("Rest").build(),
                PlanDays.builder().dayNumber(3).plan(plan).workoutSessions(List.of(mobilitySession)).build(),
                PlanDays.builder().dayNumber(4).plan(plan).note("Rest").build(),
                PlanDays.builder().dayNumber(5).plan(plan).workoutSessions(List.of(repeatFullBodyA)).build(),
                PlanDays.builder().dayNumber(6).plan(plan).note("Rest").build(),
                PlanDays.builder().dayNumber(7).plan(plan).note("Rest").build()
        );

        plan.setPlanDays(days);
        plansRepo.save(plan);
    }

    private void insertPlan8Data(
            PlansRepository plansRepo,
            PlanDaysRepository planDaysRepo,
            WorkoutSessionsRepository sessionRepo,
            ExerciseRepository exerciseRepo,
            BodyFocusRepository bodyFocusRepo
    ) {
        BodyFocus chest = getOrCreateBodyFocus("Chest Muscles", bodyFocusRepo);
        BodyFocus back = getOrCreateBodyFocus("Back Muscles", bodyFocusRepo);
        BodyFocus armShoulder = getOrCreateBodyFocus("Arm & Shoulder", bodyFocusRepo);
        BodyFocus legButt = getOrCreateBodyFocus("Leg & Butt", bodyFocusRepo);
        BodyFocus sixPack = getOrCreateBodyFocus("Six Pack", bodyFocusRepo);
        BodyFocus cardio = getOrCreateBodyFocus("Cardio", bodyFocusRepo);

        // ✅ Push A
        List<Exercise> pushA = List.of(
                buildExercise("Barbell Bench Press", "Heavy set", 4, "6-8", 120, 42, 168, Set.of(chest), null),
                buildExercise("Standing Overhead Press", "Barbell or Dumbbells", 4, "8-10", 90, 36, 144, Set.of(armShoulder), null),
                buildExercise("Incline Dumbbell Press", "Upper chest focus", 3, "10-12", 90, 34, 102, Set.of(chest), null),
                buildExercise("Cable Lateral Raises", "Strict form", 3, "12-15", 60, 28, 84, Set.of(armShoulder), null),
                buildExercise("Tricep Dips", "Weighted if advanced", 3, "10-12", 90, 30, 90, Set.of(armShoulder), null)
        ).stream().map(exerciseRepo::save).toList();
        WorkoutSessions pushSessionA = sessionRepo.save(WorkoutSessions.builder()
                .name("Push A").trainingLevel(TrainingLevel.ADVANCED).exercises(pushA).build());

        // ✅ Pull A
        List<Exercise> pullA = List.of(
                buildExercise("Barbell Deadlift", "Heavy", 4, "5-6", 150, 50, 200, Set.of(back), null),
                buildExercise("Pull Ups", "Weighted if possible", 4, "AMRAP", 90, 34, 136, Set.of(back, armShoulder), null),
                buildExercise("Barbell Row", "Strict back control", 4, "8-10", 90, 36, 144, Set.of(back), null),
                buildExercise("Face Pulls", "Rear delt focus", 3, "15-20", 60, 28, 84, Set.of(back, armShoulder), null),
                buildExercise("Barbell Curl", "Heavy", 3, "10-12", 60, 30, 90, Set.of(armShoulder), null)
        ).stream().map(exerciseRepo::save).toList();
        WorkoutSessions pullSessionA = sessionRepo.save(WorkoutSessions.builder()
                .name("Pull A").trainingLevel(TrainingLevel.ADVANCED).exercises(pullA).build());

        // ✅ Legs A
        List<Exercise> legsA = List.of(
                buildExercise("Barbell Back Squat", "Heavy, below parallel", 4, "6-8", 150, 45, 180, Set.of(legButt), null),
                buildExercise("Romanian Deadlift", "Hamstring focus", 4, "8-10", 120, 40, 160, Set.of(legButt, back), null),
                buildExercise("Walking Lunges", "With Dumbbells", 3, "12 per leg", 90, 36, 108, Set.of(legButt), null),
                buildExercise("Calf Raise", "Slow & controlled", 4, "15-20", 60, 28, 112, Set.of(legButt), null)
        ).stream().map(exerciseRepo::save).toList();
        WorkoutSessions legsSessionA = sessionRepo.save(WorkoutSessions.builder()
                .name("Legs A").trainingLevel(TrainingLevel.ADVANCED).exercises(legsA).build());

        // ✅ Core & Cardio
        List<Exercise> coreCardio = List.of(
                buildExercise("Plank", "Hold tight core", 3, "60s hold", 60, 24, 72, Set.of(sixPack), 60),
                buildExercise("Bicycle Crunch", "Fast tempo", 3, "20 per side", 60, 26, 78, Set.of(sixPack), null),
                buildExercise("Mountain Climbers", "Fast pace", 3, "20 per side", 45, 28, 84, Set.of(cardio, sixPack), null),
                buildExercise("Burpees", "Explosive", 3, "15", 60, 35, 105, Set.of(cardio), null)
        ).stream().map(exerciseRepo::save).toList();
        WorkoutSessions coreCardioSession = sessionRepo.save(WorkoutSessions.builder()
                .name("Core & Cardio").trainingLevel(TrainingLevel.ADVANCED).exercises(coreCardio).build());

        // ✅ Push B
        List<Exercise> pushB = List.of(
                buildExercise("Dumbbell Shoulder Press", "Alternate arms", 4, "8-10", 90, 34, 136, Set.of(armShoulder), null),
                buildExercise("Flat Dumbbell Press", "Medium weight", 4, "10-12", 90, 35, 140, Set.of(chest), null),
                buildExercise("Overhead Tricep Extension", "Dumbbell", 3, "12-15", 60, 26, 78, Set.of(armShoulder), null),
                buildExercise("Chest Fly (Machine)", "Controlled", 3, "12-15", 60, 28, 84, Set.of(chest), null)
        ).stream().map(exerciseRepo::save).toList();
        WorkoutSessions pushSessionB = sessionRepo.save(WorkoutSessions.builder()
                .name("Push B").trainingLevel(TrainingLevel.ADVANCED).exercises(pushB).build());

        // ✅ Legs B
        List<Exercise> legsB = List.of(
                buildExercise("Front Squat", "Upright posture", 4, "8-10", 120, 42, 168, Set.of(legButt), null),
                buildExercise("Glute Bridge", "Barbell or Dumbbell", 3, "12-15", 90, 36, 108, Set.of(legButt), null),
                buildExercise("Leg Extension", "Machine", 3, "12-15", 60, 30, 90, Set.of(legButt), null),
                buildExercise("Calf Raise", "Single leg variation", 3, "15", 45, 28, 84, Set.of(legButt), null)
        ).stream().map(exerciseRepo::save).toList();
        WorkoutSessions legsSessionB = sessionRepo.save(WorkoutSessions.builder()
                .name("Legs B").trainingLevel(TrainingLevel.ADVANCED).exercises(legsB).build());

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

    void inserUser() {
        if (userRepository.count() == 0) {
            Trainee trainee1 = Trainee.builder()
                    .username("abdo")
                    .email("abdo@example.com")
                    .password(passwordEncoder.encode("123456"))
                    .role(Role.TRAINEE)
                    .gender(Gender.MALE)
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
                    .enabled(true)
                    .height(180.0)
                    .weight(90.0)
                    .targetWeight(80.0)
                    .build();

            userRepository.saveAll(List.of(trainee1, trainee2, trainee3));
        }
    }

    void insertFoodItems() {
        if (foodItemRepository.count() > 0) {
            log.info("✅ Food items already exist. Skipping...");
            return;
        }

        List<FoodItem> foodItems = List.of(

                FoodItem.builder()
                        .name("Boiled eggs and veggies")
                        .calories(349.0)
                        .protein(23.0)
                        .fat(23.5)
                        .carbs(16.8)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Barley and Broccoli")
                        .calories(484.0)
                        .protein(34.6)
                        .fat(5.3)
                        .carbs(29.5)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Barley and Carrots")
                        .calories(481.0)
                        .protein(29.1)
                        .fat(14.0)
                        .carbs(14.8)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Barley and Peppers")
                        .calories(470.0)
                        .protein(43.8)
                        .fat(18.8)
                        .carbs(16.2)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Barley and Spinach")
                        .calories(452.0)
                        .protein(29.7)
                        .fat(16.5)
                        .carbs(51.4)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Barley and Zucchini")
                        .calories(304.0)
                        .protein(40.0)
                        .fat(17.3)
                        .carbs(17.8)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Oats and Carrots")
                        .calories(417.0)
                        .protein(27.5)
                        .fat(13.1)
                        .carbs(39.3)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Oats and Kale")
                        .calories(414.0)
                        .protein(42.1)
                        .fat(7.0)
                        .carbs(36.4)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Oats and Peppers")
                        .calories(483.0)
                        .protein(28.6)
                        .fat(6.0)
                        .carbs(36.9)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Oats and Spinach")
                        .calories(463.0)
                        .protein(30.7)
                        .fat(17.1)
                        .carbs(36.6)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Pasta and Broccoli")
                        .calories(399.0)
                        .protein(29.7)
                        .fat(5.1)
                        .carbs(33.2)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Pasta and Mushrooms")
                        .calories(490.0)
                        .protein(42.4)
                        .fat(7.8)
                        .carbs(27.7)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Pasta and Spinach")
                        .calories(483.0)
                        .protein(35.8)
                        .fat(15.7)
                        .carbs(18.4)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Pasta and Zucchini")
                        .calories(434.0)
                        .protein(42.1)
                        .fat(5.1)
                        .carbs(31.3)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Sweet Potato and Broccoli")
                        .calories(538.0)
                        .protein(32.6)
                        .fat(22.4)
                        .carbs(39.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Sweet Potato and Kale")
                        .calories(534.0)
                        .protein(33.3)
                        .fat(13.3)
                        .carbs(52.5)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Sweet Potato and Mushrooms")
                        .calories(485.0)
                        .protein(33.7)
                        .fat(14.3)
                        .carbs(35.8)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Tortilla and Carrots")
                        .calories(487.0)
                        .protein(38.8)
                        .fat(12.6)
                        .carbs(25.4)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Tortilla and Peppers")
                        .calories(441.0)
                        .protein(45.0)
                        .fat(6.8)
                        .carbs(32.1)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Tortilla and Spinach")
                        .calories(471.0)
                        .protein(48.8)
                        .fat(18.7)
                        .carbs(33.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Whole Wheat Bread and Broccoli")
                        .calories(458.0)
                        .protein(32.9)
                        .fat(6.9)
                        .carbs(32.2)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Whole Wheat Bread and Carrots")
                        .calories(468.0)
                        .protein(41.4)
                        .fat(15.6)
                        .carbs(25.7)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Whole Wheat Bread and Kale")
                        .calories(470.0)
                        .protein(32.4)
                        .fat(14.7)
                        .carbs(39.3)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Scrambled eggs and toast")
                        .calories(403.0)
                        .protein(25.7)
                        .fat(23.9)
                        .carbs(26.4)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Whole eggs and toast")
                        .calories(541.0)
                        .protein(31.6)
                        .fat(23.2)
                        .carbs(37.6)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggplant with Oats")
                        .calories(414.0)
                        .protein(14.0)
                        .fat(6.8)
                        .carbs(76.4)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggplant with Cereal")
                        .calories(411.0)
                        .protein(12.9)
                        .fat(7.0)
                        .carbs(82.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggplant with Bread")
                        .calories(301.0)
                        .protein(9.7)
                        .fat(3.6)
                        .carbs(58.2)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Eggplant with Smoothie with Nuts & Oats")
                        .calories(335.0)
                        .protein(10.8)
                        .fat(12.2)
                        .carbs(43.7)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Greek Yogurt with Oats")
                        .calories(438.0)
                        .protein(23.3)
                        .fat(6.9)
                        .carbs(71.3)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Greek Yogurt with Whole Wheat Bread")
                        .calories(311.0)
                        .protein(22.6)
                        .fat(3.9)
                        .carbs(46.3)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Greek Yogurt with Cereal")
                        .calories(435.0)
                        .protein(22.3)
                        .fat(7.1)
                        .carbs(76.8)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Greek Yogurt with Bread")
                        .calories(325.0)
                        .protein(19.0)
                        .fat(3.7)
                        .carbs(53.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Greek Yogurt with Smoothie with Nuts & Oats")
                        .calories(359.0)
                        .protein(20.2)
                        .fat(12.4)
                        .carbs(38.6)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Greek Yogurt with Protein Smoothie")
                        .calories(309.0)
                        .protein(35.2)
                        .fat(5.4)
                        .carbs(23.6)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Greek Yogurt with Fruit Protein Smoothie")
                        .calories(309.0)
                        .protein(30.2)
                        .fat(6.4)
                        .carbs(28.6)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Cottage Cheese with Oats")
                        .calories(477.0)
                        .protein(24.3)
                        .fat(10.8)
                        .carbs(71.1)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Cottage Cheese with Whole Wheat Bread")
                        .calories(350.0)
                        .protein(23.6)
                        .fat(7.8)
                        .carbs(46.1)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Cottage Cheese with Cereal")
                        .calories(474.0)
                        .protein(23.2)
                        .fat(11.0)
                        .carbs(76.6)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Cottage Cheese with Bread")
                        .calories(364.0)
                        .protein(20.0)
                        .fat(7.6)
                        .carbs(52.8)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Cottage Cheese with Pancakes")
                        .calories(325.0)
                        .protein(17.5)
                        .fat(14.0)
                        .carbs(31.7)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Cottage Cheese with Soft Brown Bread")
                        .calories(328.0)
                        .protein(18.6)
                        .fat(6.3)
                        .carbs(43.4)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Cottage Cheese with Smoothie with Nuts & Oats")
                        .calories(398.0)
                        .protein(21.1)
                        .fat(16.3)
                        .carbs(38.4)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Cottage Cheese with Protein Smoothie")
                        .calories(348.0)
                        .protein(36.1)
                        .fat(9.3)
                        .carbs(23.4)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Cottage Cheese with Fruit Protein Smoothie")
                        .calories(348.0)
                        .protein(31.1)
                        .fat(10.3)
                        .carbs(28.4)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Grilled chicken salad")
                        .calories(372.0)
                        .protein(44.6)
                        .fat(14.4)
                        .carbs(12.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Chicken breast with rice")
                        .calories(700.0)
                        .protein(55.4)
                        .fat(22.8)
                        .carbs(65.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna pasta")
                        .calories(711.0)
                        .protein(37.6)
                        .fat(18.1)
                        .carbs(64.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Rice + minced beef")
                        .calories(529.0)
                        .protein(29.6)
                        .fat(20.9)
                        .carbs(63.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp and broccoli")
                        .calories(439.0)
                        .protein(36.6)
                        .fat(15.7)
                        .carbs(14.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey sandwich")
                        .calories(482.0)
                        .protein(38.1)
                        .fat(21.4)
                        .carbs(37.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Sweet Potato and Mushrooms")
                        .calories(476.0)
                        .protein(49.9)
                        .fat(14.8)
                        .carbs(37.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Pasta and Spinach")
                        .calories(735.0)
                        .protein(31.3)
                        .fat(24.1)
                        .carbs(83.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Whole Wheat Bread and Zucchini")
                        .calories(648.0)
                        .protein(35.0)
                        .fat(16.0)
                        .carbs(58.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Whole Wheat Bread and Peppers")
                        .calories(575.0)
                        .protein(44.0)
                        .fat(24.8)
                        .carbs(53.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Pasta and Green Beans")
                        .calories(338.0)
                        .protein(35.4)
                        .fat(12.2)
                        .carbs(10.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Pasta and Mushrooms")
                        .calories(392.0)
                        .protein(41.7)
                        .fat(12.1)
                        .carbs(22.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Pasta and Spinach")
                        .calories(505.0)
                        .protein(36.6)
                        .fat(19.7)
                        .carbs(39.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Whole Wheat Bread and Carrots")
                        .calories(530.0)
                        .protein(27.6)
                        .fat(13.0)
                        .carbs(58.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Whole Wheat Bread and Zucchini")
                        .calories(411.0)
                        .protein(42.3)
                        .fat(11.8)
                        .carbs(35.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Sweet Potato and Spinach")
                        .calories(362.0)
                        .protein(39.1)
                        .fat(5.9)
                        .carbs(28.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Brown Rice and Carrots")
                        .calories(357.0)
                        .protein(44.0)
                        .fat(5.0)
                        .carbs(11.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Whole Wheat Bread and Zucchini")
                        .calories(755.0)
                        .protein(53.0)
                        .fat(34.4)
                        .carbs(70.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Sweet Potato and Broccoli")
                        .calories(551.0)
                        .protein(28.1)
                        .fat(13.5)
                        .carbs(55.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Whole Wheat Bread and Carrots")
                        .calories(775.0)
                        .protein(49.3)
                        .fat(23.8)
                        .carbs(98.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Pasta and Zucchini")
                        .calories(785.0)
                        .protein(33.6)
                        .fat(32.5)
                        .carbs(90.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Sweet Potato and Mushrooms")
                        .calories(497.0)
                        .protein(35.2)
                        .fat(20.2)
                        .carbs(44.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Brown Rice and Peppers")
                        .calories(490.0)
                        .protein(36.3)
                        .fat(16.9)
                        .carbs(37.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Whole Wheat Bread and Green Beans")
                        .calories(641.0)
                        .protein(40.6)
                        .fat(12.2)
                        .carbs(35.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Whole Wheat Bread and Broccoli")
                        .calories(385.0)
                        .protein(43.3)
                        .fat(17.7)
                        .carbs(15.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Pasta and Green Beans")
                        .calories(491.0)
                        .protein(34.9)
                        .fat(10.9)
                        .carbs(21.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Sweet Potato and Zucchini")
                        .calories(617.0)
                        .protein(44.6)
                        .fat(24.4)
                        .carbs(45.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Brown Rice and Peppers")
                        .calories(621.0)
                        .protein(46.8)
                        .fat(15.4)
                        .carbs(35.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Pasta and Carrots")
                        .calories(478.0)
                        .protein(26.7)
                        .fat(10.9)
                        .carbs(12.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Pasta and Carrots")
                        .calories(691.0)
                        .protein(49.6)
                        .fat(22.4)
                        .carbs(93.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Pasta and Broccoli")
                        .calories(759.0)
                        .protein(37.3)
                        .fat(33.2)
                        .carbs(66.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Whole Wheat Bread and Zucchini")
                        .calories(634.0)
                        .protein(38.2)
                        .fat(20.3)
                        .carbs(57.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Whole Wheat Bread and Carrots")
                        .calories(728.0)
                        .protein(33.6)
                        .fat(25.3)
                        .carbs(83.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Whole Wheat Bread and Mushrooms")
                        .calories(544.0)
                        .protein(49.6)
                        .fat(20.3)
                        .carbs(39.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Sweet Potato and Peppers")
                        .calories(787.0)
                        .protein(48.6)
                        .fat(32.4)
                        .carbs(76.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Pasta and Peppers")
                        .calories(439.0)
                        .protein(30.4)
                        .fat(17.0)
                        .carbs(30.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Pasta and Green Beans")
                        .calories(422.0)
                        .protein(31.2)
                        .fat(19.6)
                        .carbs(31.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Brown Rice and Mushrooms")
                        .calories(494.0)
                        .protein(26.2)
                        .fat(16.5)
                        .carbs(28.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Sweet Potato and Green Beans")
                        .calories(554.0)
                        .protein(27.4)
                        .fat(11.0)
                        .carbs(37.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Whole Wheat Bread and Broccoli")
                        .calories(791.0)
                        .protein(35.7)
                        .fat(30.6)
                        .carbs(65.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Sweet Potato and Peppers")
                        .calories(529.0)
                        .protein(34.3)
                        .fat(17.2)
                        .carbs(51.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Whole Wheat Bread and Green Beans")
                        .calories(396.0)
                        .protein(25.7)
                        .fat(5.8)
                        .carbs(19.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Brown Rice and Green Beans")
                        .calories(416.0)
                        .protein(30.4)
                        .fat(17.9)
                        .carbs(35.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Whole Wheat Bread and Green Beans")
                        .calories(600.0)
                        .protein(46.0)
                        .fat(30.1)
                        .carbs(70.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Whole Wheat Bread and Carrots")
                        .calories(459.0)
                        .protein(29.1)
                        .fat(19.3)
                        .carbs(41.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Sweet Potato and Zucchini")
                        .calories(546.0)
                        .protein(38.1)
                        .fat(23.1)
                        .carbs(45.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Brown Rice and Zucchini")
                        .calories(615.0)
                        .protein(38.3)
                        .fat(13.4)
                        .carbs(49.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Brown Rice and Mushrooms")
                        .calories(377.0)
                        .protein(30.8)
                        .fat(10.7)
                        .carbs(14.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Pasta and Broccoli")
                        .calories(473.0)
                        .protein(25.2)
                        .fat(15.0)
                        .carbs(37.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Pasta and Mushrooms")
                        .calories(339.0)
                        .protein(42.3)
                        .fat(15.6)
                        .carbs(34.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Whole Wheat Bread and Broccoli")
                        .calories(573.0)
                        .protein(30.7)
                        .fat(22.2)
                        .carbs(31.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Brown Rice and Carrots")
                        .calories(334.0)
                        .protein(31.8)
                        .fat(15.3)
                        .carbs(14.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Sweet Potato and Spinach")
                        .calories(503.0)
                        .protein(46.0)
                        .fat(13.4)
                        .carbs(55.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Sweet Potato and Spinach")
                        .calories(307.0)
                        .protein(32.8)
                        .fat(14.8)
                        .carbs(16.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Whole Wheat Bread and Broccoli")
                        .calories(679.0)
                        .protein(59.3)
                        .fat(25.3)
                        .carbs(93.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Whole Wheat Bread and Green Beans")
                        .calories(714.0)
                        .protein(57.1)
                        .fat(26.4)
                        .carbs(94.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Pasta and Carrots")
                        .calories(579.0)
                        .protein(38.9)
                        .fat(17.8)
                        .carbs(43.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Whole Wheat Bread and Green Beans")
                        .calories(788.0)
                        .protein(54.3)
                        .fat(23.7)
                        .carbs(71.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Whole Wheat Bread and Zucchini")
                        .calories(615.0)
                        .protein(35.9)
                        .fat(14.8)
                        .carbs(58.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Brown Rice and Green Beans")
                        .calories(456.0)
                        .protein(43.3)
                        .fat(18.2)
                        .carbs(28.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Brown Rice and Green Beans")
                        .calories(488.0)
                        .protein(39.4)
                        .fat(21.5)
                        .carbs(30.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Brown Rice and Spinach")
                        .calories(371.0)
                        .protein(27.0)
                        .fat(5.4)
                        .carbs(25.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Sweet Potato and Zucchini")
                        .calories(405.0)
                        .protein(42.8)
                        .fat(11.9)
                        .carbs(14.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Whole Wheat Bread and Carrots")
                        .calories(675.0)
                        .protein(33.9)
                        .fat(24.8)
                        .carbs(69.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Sweet Potato and Mushrooms")
                        .calories(392.0)
                        .protein(37.1)
                        .fat(16.8)
                        .carbs(30.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Sweet Potato and Mushrooms")
                        .calories(662.0)
                        .protein(36.0)
                        .fat(26.6)
                        .carbs(75.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Sweet Potato and Mushrooms")
                        .calories(627.0)
                        .protein(46.7)
                        .fat(22.2)
                        .carbs(65.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Sweet Potato and Zucchini")
                        .calories(479.0)
                        .protein(33.0)
                        .fat(16.9)
                        .carbs(37.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Sweet Potato and Zucchini")
                        .calories(353.0)
                        .protein(34.0)
                        .fat(12.1)
                        .carbs(39.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Whole Wheat Bread and Mushrooms")
                        .calories(641.0)
                        .protein(30.8)
                        .fat(28.7)
                        .carbs(71.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Brown Rice and Broccoli")
                        .calories(517.0)
                        .protein(43.1)
                        .fat(18.0)
                        .carbs(32.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Pasta and Zucchini")
                        .calories(452.0)
                        .protein(37.7)
                        .fat(11.9)
                        .carbs(39.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Pasta and Mushrooms")
                        .calories(524.0)
                        .protein(29.3)
                        .fat(20.8)
                        .carbs(59.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Whole Wheat Bread and Mushrooms")
                        .calories(422.0)
                        .protein(44.0)
                        .fat(18.0)
                        .carbs(16.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Sweet Potato and Carrots")
                        .calories(389.0)
                        .protein(42.1)
                        .fat(15.8)
                        .carbs(35.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Sweet Potato and Zucchini")
                        .calories(312.0)
                        .protein(42.3)
                        .fat(12.2)
                        .carbs(27.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Pasta and Spinach")
                        .calories(631.0)
                        .protein(27.8)
                        .fat(11.1)
                        .carbs(48.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Brown Rice and Zucchini")
                        .calories(473.0)
                        .protein(28.3)
                        .fat(6.6)
                        .carbs(12.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Brown Rice and Zucchini")
                        .calories(763.0)
                        .protein(56.5)
                        .fat(32.4)
                        .carbs(98.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Whole Wheat Bread and Carrots")
                        .calories(685.0)
                        .protein(58.0)
                        .fat(21.4)
                        .carbs(82.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Sweet Potato and Peppers")
                        .calories(530.0)
                        .protein(48.1)
                        .fat(16.5)
                        .carbs(37.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Sweet Potato and Mushrooms")
                        .calories(502.0)
                        .protein(31.6)
                        .fat(23.4)
                        .carbs(35.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Whole Wheat Bread and Mushrooms")
                        .calories(431.0)
                        .protein(44.4)
                        .fat(6.5)
                        .carbs(23.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Sweet Potato and Broccoli")
                        .calories(688.0)
                        .protein(46.9)
                        .fat(33.6)
                        .carbs(67.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Sweet Potato and Mushrooms")
                        .calories(637.0)
                        .protein(47.5)
                        .fat(16.7)
                        .carbs(37.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Brown Rice and Peppers")
                        .calories(507.0)
                        .protein(27.6)
                        .fat(22.0)
                        .carbs(48.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Whole Wheat Bread and Zucchini")
                        .calories(601.0)
                        .protein(38.5)
                        .fat(23.0)
                        .carbs(36.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Whole Wheat Bread and Zucchini")
                        .calories(424.0)
                        .protein(29.6)
                        .fat(16.9)
                        .carbs(37.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Whole Wheat Bread and Spinach")
                        .calories(610.0)
                        .protein(39.9)
                        .fat(22.1)
                        .carbs(81.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Sweet Potato and Peppers")
                        .calories(628.0)
                        .protein(31.7)
                        .fat(11.6)
                        .carbs(38.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Sweet Potato and Peppers")
                        .calories(748.0)
                        .protein(41.0)
                        .fat(32.5)
                        .carbs(83.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Sweet Potato and Spinach")
                        .calories(644.0)
                        .protein(40.4)
                        .fat(29.7)
                        .carbs(64.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Pasta and Peppers")
                        .calories(726.0)
                        .protein(37.0)
                        .fat(33.7)
                        .carbs(88.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Whole Wheat Bread and Mushrooms")
                        .calories(537.0)
                        .protein(26.0)
                        .fat(11.6)
                        .carbs(51.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Pasta and Spinach")
                        .calories(458.0)
                        .protein(36.3)
                        .fat(6.9)
                        .carbs(31.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Whole Wheat Bread and Zucchini")
                        .calories(381.0)
                        .protein(35.7)
                        .fat(15.7)
                        .carbs(38.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Pasta and Broccoli")
                        .calories(770.0)
                        .protein(46.5)
                        .fat(28.2)
                        .carbs(55.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Sweet Potato and Mushrooms")
                        .calories(523.0)
                        .protein(31.9)
                        .fat(22.3)
                        .carbs(41.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Pasta and Peppers")
                        .calories(352.0)
                        .protein(42.3)
                        .fat(15.5)
                        .carbs(17.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Brown Rice and Mushrooms")
                        .calories(572.0)
                        .protein(39.5)
                        .fat(24.3)
                        .carbs(32.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Whole Wheat Bread and Carrots")
                        .calories(450.0)
                        .protein(30.0)
                        .fat(12.8)
                        .carbs(23.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Whole Wheat Bread and Zucchini")
                        .calories(627.0)
                        .protein(31.4)
                        .fat(20.1)
                        .carbs(55.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Whole Wheat Bread and Broccoli")
                        .calories(680.0)
                        .protein(31.8)
                        .fat(34.8)
                        .carbs(70.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Pasta and Broccoli")
                        .calories(769.0)
                        .protein(43.6)
                        .fat(20.7)
                        .carbs(73.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Pasta and Zucchini")
                        .calories(500.0)
                        .protein(29.9)
                        .fat(19.9)
                        .carbs(11.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Sweet Potato and Zucchini")
                        .calories(495.0)
                        .protein(35.4)
                        .fat(23.4)
                        .carbs(54.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Whole Wheat Bread and Peppers")
                        .calories(724.0)
                        .protein(47.0)
                        .fat(31.1)
                        .carbs(77.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Brown Rice and Spinach")
                        .calories(349.0)
                        .protein(30.0)
                        .fat(17.4)
                        .carbs(18.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Whole Wheat Bread and Spinach")
                        .calories(369.0)
                        .protein(34.9)
                        .fat(7.1)
                        .carbs(32.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Pasta and Green Beans")
                        .calories(791.0)
                        .protein(47.7)
                        .fat(25.7)
                        .carbs(84.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Whole Wheat Bread and Spinach")
                        .calories(465.0)
                        .protein(48.9)
                        .fat(20.0)
                        .carbs(57.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Whole Wheat Bread and Mushrooms")
                        .calories(699.0)
                        .protein(57.6)
                        .fat(28.0)
                        .carbs(82.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Sweet Potato and Broccoli")
                        .calories(631.0)
                        .protein(41.2)
                        .fat(29.0)
                        .carbs(61.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Sweet Potato and Spinach")
                        .calories(440.0)
                        .protein(38.4)
                        .fat(17.6)
                        .carbs(36.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Brown Rice and Green Beans")
                        .calories(616.0)
                        .protein(30.7)
                        .fat(20.1)
                        .carbs(36.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Brown Rice and Mushrooms")
                        .calories(544.0)
                        .protein(49.2)
                        .fat(16.7)
                        .carbs(53.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Whole Wheat Bread and Spinach")
                        .calories(468.0)
                        .protein(37.6)
                        .fat(12.0)
                        .carbs(36.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Sweet Potato and Mushrooms")
                        .calories(522.0)
                        .protein(26.2)
                        .fat(19.9)
                        .carbs(38.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Sweet Potato and Carrots")
                        .calories(492.0)
                        .protein(30.9)
                        .fat(17.8)
                        .carbs(25.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Sweet Potato and Carrots")
                        .calories(375.0)
                        .protein(40.7)
                        .fat(13.2)
                        .carbs(38.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Whole Wheat Bread and Green Beans")
                        .calories(666.0)
                        .protein(36.8)
                        .fat(31.4)
                        .carbs(60.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Brown Rice and Carrots")
                        .calories(570.0)
                        .protein(27.3)
                        .fat(18.2)
                        .carbs(53.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Sweet Potato and Carrots")
                        .calories(600.0)
                        .protein(56.2)
                        .fat(22.2)
                        .carbs(69.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Brown Rice and Spinach")
                        .calories(373.0)
                        .protein(32.7)
                        .fat(18.9)
                        .carbs(34.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Sweet Potato and Green Beans")
                        .calories(467.0)
                        .protein(30.0)
                        .fat(14.4)
                        .carbs(34.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Pasta and Green Beans")
                        .calories(468.0)
                        .protein(33.3)
                        .fat(11.0)
                        .carbs(12.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Sweet Potato and Peppers")
                        .calories(726.0)
                        .protein(36.5)
                        .fat(29.0)
                        .carbs(68.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Whole Wheat Bread and Zucchini")
                        .calories(529.0)
                        .protein(47.9)
                        .fat(12.5)
                        .carbs(41.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Sweet Potato and Green Beans")
                        .calories(645.0)
                        .protein(40.6)
                        .fat(22.2)
                        .carbs(74.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Sweet Potato and Carrots")
                        .calories(418.0)
                        .protein(39.9)
                        .fat(6.4)
                        .carbs(36.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Brown Rice and Zucchini")
                        .calories(560.0)
                        .protein(44.7)
                        .fat(18.5)
                        .carbs(55.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Pasta and Green Beans")
                        .calories(791.0)
                        .protein(50.1)
                        .fat(20.3)
                        .carbs(75.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Brown Rice and Broccoli")
                        .calories(586.0)
                        .protein(44.7)
                        .fat(15.0)
                        .carbs(30.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Sweet Potato and Green Beans")
                        .calories(616.0)
                        .protein(37.1)
                        .fat(11.8)
                        .carbs(31.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Pasta and Green Beans")
                        .calories(484.0)
                        .protein(30.1)
                        .fat(22.8)
                        .carbs(43.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Whole Wheat Bread and Spinach")
                        .calories(300.0)
                        .protein(37.6)
                        .fat(10.5)
                        .carbs(27.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Sweet Potato and Peppers")
                        .calories(741.0)
                        .protein(44.6)
                        .fat(20.7)
                        .carbs(88.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Whole Wheat Bread and Zucchini")
                        .calories(695.0)
                        .protein(30.0)
                        .fat(24.9)
                        .carbs(52.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Brown Rice and Peppers")
                        .calories(769.0)
                        .protein(50.2)
                        .fat(28.9)
                        .carbs(55.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Brown Rice and Mushrooms")
                        .calories(313.0)
                        .protein(28.2)
                        .fat(8.6)
                        .carbs(30.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Pasta and Peppers")
                        .calories(479.0)
                        .protein(35.9)
                        .fat(22.7)
                        .carbs(32.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Sweet Potato and Carrots")
                        .calories(517.0)
                        .protein(39.5)
                        .fat(15.0)
                        .carbs(47.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Pasta and Green Beans")
                        .calories(612.0)
                        .protein(41.3)
                        .fat(24.9)
                        .carbs(36.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Whole Wheat Bread and Green Beans")
                        .calories(538.0)
                        .protein(48.5)
                        .fat(19.4)
                        .carbs(42.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Brown Rice and Carrots")
                        .calories(636.0)
                        .protein(38.3)
                        .fat(13.3)
                        .carbs(50.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Sweet Potato and Peppers")
                        .calories(594.0)
                        .protein(28.5)
                        .fat(16.8)
                        .carbs(50.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Whole Wheat Bread and Carrots")
                        .calories(421.0)
                        .protein(25.7)
                        .fat(19.8)
                        .carbs(26.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Sweet Potato and Green Beans")
                        .calories(408.0)
                        .protein(35.2)
                        .fat(13.1)
                        .carbs(14.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Whole Wheat Bread and Green Beans")
                        .calories(592.0)
                        .protein(43.0)
                        .fat(15.0)
                        .carbs(32.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Brown Rice and Green Beans")
                        .calories(740.0)
                        .protein(41.6)
                        .fat(30.4)
                        .carbs(67.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Sweet Potato and Carrots")
                        .calories(572.0)
                        .protein(35.6)
                        .fat(10.5)
                        .carbs(35.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Brown Rice and Spinach")
                        .calories(584.0)
                        .protein(46.5)
                        .fat(15.4)
                        .carbs(33.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Brown Rice and Broccoli")
                        .calories(524.0)
                        .protein(45.4)
                        .fat(14.9)
                        .carbs(51.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Brown Rice and Peppers")
                        .calories(691.0)
                        .protein(57.7)
                        .fat(34.9)
                        .carbs(75.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Brown Rice and Zucchini")
                        .calories(764.0)
                        .protein(52.2)
                        .fat(22.5)
                        .carbs(94.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Brown Rice and Broccoli")
                        .calories(687.0)
                        .protein(48.2)
                        .fat(25.4)
                        .carbs(67.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Whole Wheat Bread and Green Beans")
                        .calories(645.0)
                        .protein(38.1)
                        .fat(24.3)
                        .carbs(46.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Brown Rice and Mushrooms")
                        .calories(491.0)
                        .protein(37.3)
                        .fat(17.2)
                        .carbs(27.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Whole Wheat Bread and Peppers")
                        .calories(526.0)
                        .protein(35.1)
                        .fat(12.5)
                        .carbs(54.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Brown Rice and Green Beans")
                        .calories(683.0)
                        .protein(36.8)
                        .fat(23.8)
                        .carbs(92.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Sweet Potato and Spinach")
                        .calories(457.0)
                        .protein(28.6)
                        .fat(13.2)
                        .carbs(37.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Pasta and Zucchini")
                        .calories(798.0)
                        .protein(30.1)
                        .fat(33.5)
                        .carbs(97.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Pasta and Mushrooms")
                        .calories(483.0)
                        .protein(36.1)
                        .fat(14.4)
                        .carbs(57.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Sweet Potato and Peppers")
                        .calories(327.0)
                        .protein(43.6)
                        .fat(13.6)
                        .carbs(16.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Pasta and Carrots")
                        .calories(330.0)
                        .protein(28.5)
                        .fat(12.3)
                        .carbs(27.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Pasta and Mushrooms")
                        .calories(490.0)
                        .protein(28.5)
                        .fat(16.6)
                        .carbs(24.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Sweet Potato and Mushrooms")
                        .calories(602.0)
                        .protein(29.2)
                        .fat(12.1)
                        .carbs(40.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Pasta and Zucchini")
                        .calories(470.0)
                        .protein(41.0)
                        .fat(6.4)
                        .carbs(38.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Whole Wheat Bread and Green Beans")
                        .calories(798.0)
                        .protein(47.6)
                        .fat(31.6)
                        .carbs(84.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Pasta and Broccoli")
                        .calories(702.0)
                        .protein(37.7)
                        .fat(28.9)
                        .carbs(68.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Pasta and Green Beans")
                        .calories(351.0)
                        .protein(41.1)
                        .fat(19.5)
                        .carbs(21.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Pasta and Zucchini")
                        .calories(386.0)
                        .protein(30.4)
                        .fat(17.7)
                        .carbs(10.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Whole Wheat Bread and Peppers")
                        .calories(628.0)
                        .protein(44.2)
                        .fat(18.5)
                        .carbs(33.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Brown Rice and Spinach")
                        .calories(305.0)
                        .protein(44.8)
                        .fat(8.3)
                        .carbs(20.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Whole Wheat Bread and Peppers")
                        .calories(460.0)
                        .protein(35.7)
                        .fat(14.8)
                        .carbs(49.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Brown Rice and Broccoli")
                        .calories(528.0)
                        .protein(47.0)
                        .fat(21.6)
                        .carbs(58.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Whole Wheat Bread and Carrots")
                        .calories(561.0)
                        .protein(41.4)
                        .fat(21.8)
                        .carbs(58.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Whole Wheat Bread and Mushrooms")
                        .calories(469.0)
                        .protein(42.5)
                        .fat(14.9)
                        .carbs(22.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Whole Wheat Bread and Peppers")
                        .calories(596.0)
                        .protein(42.4)
                        .fat(11.3)
                        .carbs(42.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Whole Wheat Bread and Carrots")
                        .calories(638.0)
                        .protein(51.8)
                        .fat(32.7)
                        .carbs(50.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Whole Wheat Bread and Zucchini")
                        .calories(421.0)
                        .protein(33.1)
                        .fat(12.8)
                        .carbs(22.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Pasta and Mushrooms")
                        .calories(485.0)
                        .protein(26.1)
                        .fat(14.7)
                        .carbs(56.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Sweet Potato and Green Beans")
                        .calories(525.0)
                        .protein(28.4)
                        .fat(17.5)
                        .carbs(47.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Pasta and Peppers")
                        .calories(310.0)
                        .protein(37.8)
                        .fat(5.1)
                        .carbs(38.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Brown Rice and Zucchini")
                        .calories(480.0)
                        .protein(27.0)
                        .fat(22.8)
                        .carbs(40.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Brown Rice and Spinach")
                        .calories(495.0)
                        .protein(30.9)
                        .fat(6.6)
                        .carbs(30.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Whole Wheat Bread and Carrots")
                        .calories(527.0)
                        .protein(47.7)
                        .fat(12.7)
                        .carbs(48.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Brown Rice and Mushrooms")
                        .calories(764.0)
                        .protein(58.5)
                        .fat(25.1)
                        .carbs(64.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Pasta and Carrots")
                        .calories(637.0)
                        .protein(52.0)
                        .fat(21.9)
                        .carbs(99.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Pasta and Mushrooms")
                        .calories(623.0)
                        .protein(43.4)
                        .fat(18.8)
                        .carbs(37.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Sweet Potato and Mushrooms")
                        .calories(560.0)
                        .protein(46.7)
                        .fat(24.6)
                        .carbs(39.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Pasta and Carrots")
                        .calories(441.0)
                        .protein(26.5)
                        .fat(18.7)
                        .carbs(12.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Sweet Potato and Zucchini")
                        .calories(489.0)
                        .protein(47.3)
                        .fat(16.3)
                        .carbs(44.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Brown Rice and Green Beans")
                        .calories(617.0)
                        .protein(27.7)
                        .fat(23.6)
                        .carbs(53.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Brown Rice and Spinach")
                        .calories(662.0)
                        .protein(49.9)
                        .fat(33.4)
                        .carbs(59.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Whole Wheat Bread and Spinach")
                        .calories(328.0)
                        .protein(30.4)
                        .fat(13.6)
                        .carbs(31.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Pasta and Broccoli")
                        .calories(570.0)
                        .protein(30.9)
                        .fat(12.3)
                        .carbs(52.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Sweet Potato and Spinach")
                        .calories(516.0)
                        .protein(30.9)
                        .fat(23.4)
                        .carbs(42.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Sweet Potato and Zucchini")
                        .calories(618.0)
                        .protein(35.8)
                        .fat(21.9)
                        .carbs(75.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Whole Wheat Bread and Peppers")
                        .calories(356.0)
                        .protein(38.5)
                        .fat(6.6)
                        .carbs(30.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Whole Wheat Bread and Peppers")
                        .calories(438.0)
                        .protein(29.0)
                        .fat(8.5)
                        .carbs(18.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Brown Rice and Carrots")
                        .calories(473.0)
                        .protein(25.3)
                        .fat(18.2)
                        .carbs(35.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Brown Rice and Peppers")
                        .calories(580.0)
                        .protein(38.0)
                        .fat(11.7)
                        .carbs(33.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Sweet Potato and Green Beans")
                        .calories(323.0)
                        .protein(37.8)
                        .fat(18.9)
                        .carbs(26.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Sweet Potato and Green Beans")
                        .calories(459.0)
                        .protein(28.2)
                        .fat(10.6)
                        .carbs(40.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Brown Rice and Carrots")
                        .calories(503.0)
                        .protein(37.9)
                        .fat(23.7)
                        .carbs(48.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Whole Wheat Bread and Mushrooms")
                        .calories(342.0)
                        .protein(26.2)
                        .fat(17.7)
                        .carbs(30.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Brown Rice and Green Beans")
                        .calories(495.0)
                        .protein(25.4)
                        .fat(8.9)
                        .carbs(26.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Pasta and Spinach")
                        .calories(768.0)
                        .protein(41.7)
                        .fat(26.1)
                        .carbs(88.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Pasta and Broccoli")
                        .calories(321.0)
                        .protein(44.0)
                        .fat(17.4)
                        .carbs(24.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Brown Rice and Carrots")
                        .calories(620.0)
                        .protein(43.2)
                        .fat(18.1)
                        .carbs(56.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Brown Rice and Peppers")
                        .calories(624.0)
                        .protein(56.5)
                        .fat(27.6)
                        .carbs(91.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Sweet Potato and Green Beans")
                        .calories(773.0)
                        .protein(56.2)
                        .fat(27.1)
                        .carbs(85.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Pasta and Peppers")
                        .calories(666.0)
                        .protein(30.9)
                        .fat(31.8)
                        .carbs(78.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Sweet Potato and Zucchini")
                        .calories(606.0)
                        .protein(27.8)
                        .fat(23.6)
                        .carbs(41.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Pasta and Mushrooms")
                        .calories(600.0)
                        .protein(27.8)
                        .fat(11.3)
                        .carbs(37.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Whole Wheat Bread and Peppers")
                        .calories(556.0)
                        .protein(26.1)
                        .fat(22.7)
                        .carbs(39.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Brown Rice and Mushrooms")
                        .calories(560.0)
                        .protein(25.0)
                        .fat(14.5)
                        .carbs(54.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Whole Wheat Bread and Zucchini")
                        .calories(327.0)
                        .protein(40.8)
                        .fat(8.9)
                        .carbs(27.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Whole Wheat Bread and Carrots")
                        .calories(752.0)
                        .protein(47.8)
                        .fat(29.9)
                        .carbs(92.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Sweet Potato and Mushrooms")
                        .calories(490.0)
                        .protein(35.5)
                        .fat(13.3)
                        .carbs(15.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Brown Rice and Zucchini")
                        .calories(610.0)
                        .protein(31.9)
                        .fat(22.5)
                        .carbs(35.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Pasta and Mushrooms")
                        .calories(421.0)
                        .protein(34.5)
                        .fat(15.2)
                        .carbs(25.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Pasta and Spinach")
                        .calories(623.0)
                        .protein(50.1)
                        .fat(28.9)
                        .carbs(60.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Sweet Potato and Mushrooms")
                        .calories(555.0)
                        .protein(32.9)
                        .fat(13.6)
                        .carbs(54.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Sweet Potato and Peppers")
                        .calories(722.0)
                        .protein(47.2)
                        .fat(22.0)
                        .carbs(60.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Pasta and Zucchini")
                        .calories(605.0)
                        .protein(46.4)
                        .fat(17.3)
                        .carbs(32.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Brown Rice and Broccoli")
                        .calories(383.0)
                        .protein(26.1)
                        .fat(13.0)
                        .carbs(23.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Pasta and Broccoli")
                        .calories(640.0)
                        .protein(37.4)
                        .fat(11.8)
                        .carbs(31.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Sweet Potato and Zucchini")
                        .calories(529.0)
                        .protein(45.8)
                        .fat(19.0)
                        .carbs(45.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Brown Rice and Spinach")
                        .calories(648.0)
                        .protein(37.4)
                        .fat(12.2)
                        .carbs(48.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Sweet Potato and Mushrooms")
                        .calories(600.0)
                        .protein(47.2)
                        .fat(22.6)
                        .carbs(58.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Sweet Potato and Carrots")
                        .calories(589.0)
                        .protein(48.5)
                        .fat(24.9)
                        .carbs(38.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Brown Rice and Broccoli")
                        .calories(375.0)
                        .protein(38.2)
                        .fat(7.2)
                        .carbs(10.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Sweet Potato and Mushrooms")
                        .calories(666.0)
                        .protein(46.5)
                        .fat(21.9)
                        .carbs(53.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Sweet Potato and Mushrooms")
                        .calories(736.0)
                        .protein(43.0)
                        .fat(25.0)
                        .carbs(70.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Pasta and Mushrooms")
                        .calories(493.0)
                        .protein(27.8)
                        .fat(11.5)
                        .carbs(31.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Brown Rice and Zucchini")
                        .calories(753.0)
                        .protein(53.0)
                        .fat(22.6)
                        .carbs(98.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Brown Rice and Carrots")
                        .calories(618.0)
                        .protein(36.0)
                        .fat(17.0)
                        .carbs(43.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Brown Rice and Mushrooms")
                        .calories(626.0)
                        .protein(29.8)
                        .fat(22.5)
                        .carbs(37.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Brown Rice and Spinach")
                        .calories(378.0)
                        .protein(30.7)
                        .fat(8.1)
                        .carbs(19.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Whole Wheat Bread and Broccoli")
                        .calories(422.0)
                        .protein(35.6)
                        .fat(14.1)
                        .carbs(12.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Sweet Potato and Carrots")
                        .calories(709.0)
                        .protein(33.0)
                        .fat(26.5)
                        .carbs(67.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Brown Rice and Spinach")
                        .calories(404.0)
                        .protein(40.7)
                        .fat(6.9)
                        .carbs(35.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Whole Wheat Bread and Peppers")
                        .calories(376.0)
                        .protein(28.8)
                        .fat(14.5)
                        .carbs(27.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Sweet Potato and Peppers")
                        .calories(663.0)
                        .protein(48.8)
                        .fat(31.9)
                        .carbs(58.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Brown Rice and Zucchini")
                        .calories(757.0)
                        .protein(49.3)
                        .fat(29.8)
                        .carbs(59.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Brown Rice and Green Beans")
                        .calories(398.0)
                        .protein(37.8)
                        .fat(14.4)
                        .carbs(22.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Pasta and Zucchini")
                        .calories(523.0)
                        .protein(35.4)
                        .fat(18.2)
                        .carbs(44.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Whole Wheat Bread and Spinach")
                        .calories(496.0)
                        .protein(38.5)
                        .fat(8.3)
                        .carbs(10.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Whole Wheat Bread and Spinach")
                        .calories(605.0)
                        .protein(43.9)
                        .fat(11.8)
                        .carbs(55.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Sweet Potato and Broccoli")
                        .calories(450.0)
                        .protein(30.2)
                        .fat(11.3)
                        .carbs(11.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Pasta and Broccoli")
                        .calories(377.0)
                        .protein(29.2)
                        .fat(12.4)
                        .carbs(28.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Brown Rice and Mushrooms")
                        .calories(383.0)
                        .protein(33.6)
                        .fat(5.9)
                        .carbs(37.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Brown Rice and Spinach")
                        .calories(692.0)
                        .protein(37.6)
                        .fat(30.6)
                        .carbs(58.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Whole Wheat Bread and Spinach")
                        .calories(733.0)
                        .protein(48.7)
                        .fat(32.3)
                        .carbs(69.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Sweet Potato and Green Beans")
                        .calories(428.0)
                        .protein(32.6)
                        .fat(18.7)
                        .carbs(37.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Brown Rice and Mushrooms")
                        .calories(735.0)
                        .protein(32.7)
                        .fat(31.1)
                        .carbs(89.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Sweet Potato and Spinach")
                        .calories(687.0)
                        .protein(40.1)
                        .fat(20.6)
                        .carbs(86.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Brown Rice and Carrots")
                        .calories(762.0)
                        .protein(35.2)
                        .fat(31.0)
                        .carbs(72.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Brown Rice and Green Beans")
                        .calories(580.0)
                        .protein(33.0)
                        .fat(22.8)
                        .carbs(50.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Sweet Potato and Mushrooms")
                        .calories(316.0)
                        .protein(30.7)
                        .fat(19.4)
                        .carbs(24.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Sweet Potato and Green Beans")
                        .calories(728.0)
                        .protein(33.6)
                        .fat(22.2)
                        .carbs(60.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Sweet Potato and Spinach")
                        .calories(347.0)
                        .protein(36.8)
                        .fat(12.1)
                        .carbs(15.2)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Whole Wheat Bread and Mushrooms")
                        .calories(499.0)
                        .protein(32.1)
                        .fat(18.9)
                        .carbs(28.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Sweet Potato and Carrots")
                        .calories(389.0)
                        .protein(32.9)
                        .fat(18.9)
                        .carbs(20.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Pasta and Mushrooms")
                        .calories(726.0)
                        .protein(49.9)
                        .fat(33.7)
                        .carbs(94.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Brown Rice and Spinach")
                        .calories(542.0)
                        .protein(36.1)
                        .fat(22.8)
                        .carbs(50.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Pasta and Carrots")
                        .calories(607.0)
                        .protein(35.8)
                        .fat(23.5)
                        .carbs(40.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Whole Wheat Bread and Peppers")
                        .calories(391.0)
                        .protein(32.1)
                        .fat(13.6)
                        .carbs(34.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Pasta and Mushrooms")
                        .calories(704.0)
                        .protein(51.4)
                        .fat(27.6)
                        .carbs(83.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Pasta and Mushrooms")
                        .calories(427.0)
                        .protein(36.1)
                        .fat(6.6)
                        .carbs(32.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Pasta and Carrots")
                        .calories(451.0)
                        .protein(28.5)
                        .fat(9.4)
                        .carbs(28.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Brown Rice and Mushrooms")
                        .calories(623.0)
                        .protein(58.2)
                        .fat(23.1)
                        .carbs(66.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Pasta and Carrots")
                        .calories(394.0)
                        .protein(36.2)
                        .fat(8.4)
                        .carbs(22.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Pasta and Zucchini")
                        .calories(486.0)
                        .protein(39.3)
                        .fat(15.5)
                        .carbs(19.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Whole Wheat Bread and Spinach")
                        .calories(646.0)
                        .protein(30.7)
                        .fat(12.9)
                        .carbs(45.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Whole Wheat Bread and Green Beans")
                        .calories(345.0)
                        .protein(25.8)
                        .fat(14.1)
                        .carbs(28.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Pasta and Peppers")
                        .calories(566.0)
                        .protein(36.7)
                        .fat(19.9)
                        .carbs(36.4)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Sweet Potato and Broccoli")
                        .calories(617.0)
                        .protein(29.1)
                        .fat(23.6)
                        .carbs(53.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Sweet Potato and Carrots")
                        .calories(743.0)
                        .protein(59.4)
                        .fat(24.4)
                        .carbs(65.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Whole Wheat Bread and Broccoli")
                        .calories(592.0)
                        .protein(49.0)
                        .fat(24.7)
                        .carbs(57.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Sweet Potato and Mushrooms")
                        .calories(540.0)
                        .protein(35.8)
                        .fat(14.4)
                        .carbs(48.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Whole Wheat Bread and Spinach")
                        .calories(420.0)
                        .protein(37.7)
                        .fat(7.7)
                        .carbs(16.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Whole Wheat Bread and Peppers")
                        .calories(410.0)
                        .protein(40.5)
                        .fat(10.5)
                        .carbs(13.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Brown Rice and Carrots")
                        .calories(733.0)
                        .protein(39.4)
                        .fat(29.8)
                        .carbs(50.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Sweet Potato and Carrots")
                        .calories(337.0)
                        .protein(26.4)
                        .fat(16.2)
                        .carbs(28.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Sweet Potato and Spinach")
                        .calories(548.0)
                        .protein(49.7)
                        .fat(14.2)
                        .carbs(52.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Pasta and Mushrooms")
                        .calories(312.0)
                        .protein(43.4)
                        .fat(11.8)
                        .carbs(24.7)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Brown Rice and Zucchini")
                        .calories(424.0)
                        .protein(36.8)
                        .fat(5.4)
                        .carbs(37.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Brown Rice and Mushrooms")
                        .calories(481.0)
                        .protein(37.3)
                        .fat(16.6)
                        .carbs(33.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Whole Wheat Bread and Peppers")
                        .calories(460.0)
                        .protein(46.9)
                        .fat(14.9)
                        .carbs(50.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Sweet Potato and Green Beans")
                        .calories(616.0)
                        .protein(41.2)
                        .fat(21.6)
                        .carbs(32.6)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Brown Rice and Peppers")
                        .calories(458.0)
                        .protein(39.8)
                        .fat(11.6)
                        .carbs(17.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Whole Wheat Bread and Peppers")
                        .calories(649.0)
                        .protein(35.1)
                        .fat(18.1)
                        .carbs(32.5)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Brown Rice and Zucchini")
                        .calories(308.0)
                        .protein(38.8)
                        .fat(15.3)
                        .carbs(21.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Turkey with Pasta and Broccoli")
                        .calories(345.0)
                        .protein(30.5)
                        .fat(5.5)
                        .carbs(33.1)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Whole Wheat Bread and Carrots")
                        .calories(714.0)
                        .protein(50.6)
                        .fat(20.6)
                        .carbs(53.8)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Pasta and Zucchini")
                        .calories(459.0)
                        .protein(45.9)
                        .fat(12.5)
                        .carbs(33.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Brown Rice and Spinach")
                        .calories(526.0)
                        .protein(29.2)
                        .fat(18.7)
                        .carbs(46.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Pasta and Zucchini")
                        .calories(671.0)
                        .protein(31.4)
                        .fat(20.1)
                        .carbs(59.9)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Ground Beef with Sweet Potato and Zucchini")
                        .calories(455.0)
                        .protein(32.8)
                        .fat(10.1)
                        .carbs(30.0)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Brown Rice and Carrots")
                        .calories(474.0)
                        .protein(28.0)
                        .fat(20.8)
                        .carbs(49.3)
                        .vegetarian(false)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("apple")
                        .calories(52.0)
                        .protein(0.26)
                        .fat(0.17)
                        .carbs(13.81)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("banana")
                        .calories(89.0)
                        .protein(1.09)
                        .fat(0.33)
                        .carbs(22.84)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("orange")
                        .calories(49.0)
                        .protein(0.91)
                        .fat(0.15)
                        .carbs(12.54)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("grapefruit")
                        .calories(42.0)
                        .protein(0.77)
                        .fat(0.14)
                        .carbs(10.66)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("strawberries")
                        .calories(32.0)
                        .protein(0.67)
                        .fat(0.3)
                        .carbs(7.68)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("blueberries")
                        .calories(57.0)
                        .protein(0.74)
                        .fat(0.33)
                        .carbs(14.49)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("raspberries")
                        .calories(52.0)
                        .protein(1.2)
                        .fat(0.65)
                        .carbs(11.94)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("mango")
                        .calories(60.0)
                        .protein(0.82)
                        .fat(0.38)
                        .carbs(14.98)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("pineapple")
                        .calories(50.0)
                        .protein(0.54)
                        .fat(0.12)
                        .carbs(13.12)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("kiwi")
                        .calories(61.0)
                        .protein(1.14)
                        .fat(0.52)
                        .carbs(14.66)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("watermelon")
                        .calories(30.0)
                        .protein(0.61)
                        .fat(0.15)
                        .carbs(7.55)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("peach")
                        .calories(39.0)
                        .protein(0.91)
                        .fat(0.25)
                        .carbs(9.54)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("pear")
                        .calories(57.0)
                        .protein(0.36)
                        .fat(0.14)
                        .carbs(15.23)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("plum")
                        .calories(46.0)
                        .protein(0.7)
                        .fat(0.28)
                        .carbs(11.42)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("cherries")
                        .calories(63.0)
                        .protein(1.06)
                        .fat(0.2)
                        .carbs(16.01)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("grapes")
                        .calories(69.0)
                        .protein(0.72)
                        .fat(0.16)
                        .carbs(18.1)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("lemon")
                        .calories(29.0)
                        .protein(1.1)
                        .fat(0.3)
                        .carbs(9.32)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("lime")
                        .calories(30.0)
                        .protein(0.7)
                        .fat(0.2)
                        .carbs(10.54)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("pomegranate")
                        .calories(83.0)
                        .protein(1.67)
                        .fat(1.17)
                        .carbs(18.7)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("figs")
                        .calories(74.0)
                        .protein(0.75)
                        .fat(0.3)
                        .carbs(19.18)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("dates")
                        .calories(282.0)
                        .protein(2.45)
                        .fat(0.39)
                        .carbs(75.03)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("prunes")
                        .calories(240.0)
                        .protein(2.18)
                        .fat(0.38)
                        .carbs(63.88)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("raisins")
                        .calories(299.0)
                        .protein(3.07)
                        .fat(0.46)
                        .carbs(79.18)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("apricots")
                        .calories(48.0)
                        .protein(1.4)
                        .fat(0.39)
                        .carbs(11.12)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("cranberries")
                        .calories(46.0)
                        .protein(0.46)
                        .fat(0.13)
                        .carbs(11.97)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("blackberries")
                        .calories(43.0)
                        .protein(1.39)
                        .fat(0.49)
                        .carbs(9.61)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("cantaloupe")
                        .calories(34.0)
                        .protein(0.84)
                        .fat(0.19)
                        .carbs(8.16)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("honeydew melon")
                        .calories(36.0)
                        .protein(0.54)
                        .fat(0.14)
                        .carbs(9.09)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("coconut")
                        .calories(456.0)
                        .protein(3.13)
                        .fat(27.99)
                        .carbs(51.85)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("guava")
                        .calories(68.0)
                        .protein(2.55)
                        .fat(0.95)
                        .carbs(14.32)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("persimmon")
                        .calories(70.0)
                        .protein(0.58)
                        .fat(0.19)
                        .carbs(18.59)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("tangerine")
                        .calories(53.0)
                        .protein(0.81)
                        .fat(0.31)
                        .carbs(13.34)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("currants")
                        .calories(283.0)
                        .protein(4.08)
                        .fat(0.27)
                        .carbs(74.08)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("carrots")
                        .calories(35.0)
                        .protein(0.76)
                        .fat(0.18)
                        .carbs(8.22)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("bell peppers")
                        .calories(27.67)
                        .protein(0.95)
                        .fat(0.2)
                        .carbs(6.57)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("tomatoes")
                        .calories(18.0)
                        .protein(0.88)
                        .fat(0.2)
                        .carbs(3.89)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("cucumber")
                        .calories(15.0)
                        .protein(0.65)
                        .fat(0.11)
                        .carbs(3.63)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("celery")
                        .calories(16.0)
                        .protein(0.69)
                        .fat(0.17)
                        .carbs(2.97)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("radishes")
                        .calories(16.0)
                        .protein(0.68)
                        .fat(0.1)
                        .carbs(3.4)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("turnips")
                        .calories(22.0)
                        .protein(0.71)
                        .fat(0.08)
                        .carbs(5.06)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("leeks")
                        .calories(31.0)
                        .protein(0.81)
                        .fat(0.2)
                        .carbs(7.62)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("bean sprouts")
                        .calories(21.0)
                        .protein(2.03)
                        .fat(0.09)
                        .carbs(4.19)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("chili peppers")
                        .calories(40.0)
                        .protein(1.87)
                        .fat(0.44)
                        .carbs(8.81)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("jalapenos")
                        .calories(29.0)
                        .protein(0.91)
                        .fat(0.37)
                        .carbs(6.5)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("sweet peppers")
                        .calories(28.0)
                        .protein(0.92)
                        .fat(0.2)
                        .carbs(6.7)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("peppers")
                        .calories(27.67)
                        .protein(0.95)
                        .fat(0.2)
                        .carbs(6.57)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("peanut butter")
                        .calories(588.0)
                        .protein(21.93)
                        .fat(49.54)
                        .carbs(23.98)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("almond butter")
                        .calories(614.0)
                        .protein(20.96)
                        .fat(55.5)
                        .carbs(18.82)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("cashew butter")
                        .calories(609.0)
                        .protein(12.12)
                        .fat(53.03)
                        .carbs(30.3)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("popcorn")
                        .calories(557.0)
                        .protein(7.5)
                        .fat(34.02)
                        .carbs(55.16)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("crackers")
                        .calories(510.0)
                        .protein(6.64)
                        .fat(26.43)
                        .carbs(61.3)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("granola")
                        .calories(489.0)
                        .protein(13.67)
                        .fat(24.31)
                        .carbs(53.88)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("waffles")
                        .calories(291.0)
                        .protein(7.9)
                        .fat(14.1)
                        .carbs(32.9)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("butter")
                        .calories(717.0)
                        .protein(0.85)
                        .fat(81.11)
                        .carbs(0.06)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("cream")
                        .calories(340.0)
                        .protein(2.84)
                        .fat(36.08)
                        .carbs(2.74)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("sour cream")
                        .calories(198.0)
                        .protein(2.44)
                        .fat(19.35)
                        .carbs(4.63)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("almonds")
                        .calories(598.0)
                        .protein(20.96)
                        .fat(52.54)
                        .carbs(21.01)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("walnuts")
                        .calories(654.0)
                        .protein(15.23)
                        .fat(65.21)
                        .carbs(13.71)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("peanuts")
                        .calories(587.0)
                        .protein(24.0)
                        .fat(50.0)
                        .carbs(21.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("flax seeds")
                        .calories(534.0)
                        .protein(18.29)
                        .fat(42.16)
                        .carbs(28.88)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("dark chocolate")
                        .calories(546.0)
                        .protein(4.88)
                        .fat(31.28)
                        .carbs(61.17)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Beetroot Juice")
                        .calories(43.0)
                        .protein(1.6)
                        .fat(0.2)
                        .carbs(10.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Nitric Oxide Booster Drink")
                        .calories(25.0)
                        .protein(0.0)
                        .fat(0.0)
                        .carbs(6.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Creatine Drink")
                        .calories(0.0)
                        .protein(0.0)
                        .fat(0.0)
                        .carbs(0.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Whey Protein Shake")
                        .calories(130.0)
                        .protein(24.0)
                        .fat(1.5)
                        .carbs(3.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Chicken with Sweet Potato with Broccoli")
                        .calories(480.0)
                        .protein(45.0)
                        .fat(15.0)
                        .carbs(30.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Fish with Brown Rice with Sauteed Vegetables")
                        .calories(500.0)
                        .protein(42.0)
                        .fat(18.0)
                        .carbs(35.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("5 Egg Whites with 1 Yolk with Toast with Veggies")
                        .calories(350.0)
                        .protein(30.0)
                        .fat(12.0)
                        .carbs(25.0)
                        .vegetarian(true)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Brown Bread with Lettuce")
                        .calories(400.0)
                        .protein(35.0)
                        .fat(10.0)
                        .carbs(28.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Lean Beef Burger with Brown Toast with Tomato")
                        .calories(450.0)
                        .protein(38.0)
                        .fat(14.0)
                        .carbs(33.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Shrimp with Potato with Salad")
                        .calories(430.0)
                        .protein(40.0)
                        .fat(10.0)
                        .carbs(30.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Chicken Kofta with Brown Rice with Veggies")
                        .calories(470.0)
                        .protein(43.0)
                        .fat(13.0)
                        .carbs(35.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("1 Egg with 3 Whites with Toast with Veggies")
                        .calories(340.0)
                        .protein(28.0)
                        .fat(11.0)
                        .carbs(27.0)
                        .vegetarian(true)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Savory Oats with Cucumber")
                        .calories(420.0)
                        .protein(36.0)
                        .fat(12.0)
                        .carbs(30.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Chicken with Tuna Salad with Half Toast")
                        .calories(460.0)
                        .protein(44.0)
                        .fat(13.0)
                        .carbs(29.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Green Beans with Potato")
                        .calories(510.0)
                        .protein(38.0)
                        .fat(20.0)
                        .carbs(32.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Cottage Cheese with Toast with Arugula")
                        .calories(370.0)
                        .protein(30.0)
                        .fat(13.0)
                        .carbs(26.0)
                        .vegetarian(true)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Egg Omelet with Brown Bread")
                        .calories(390.0)
                        .protein(32.0)
                        .fat(12.0)
                        .carbs(28.0)
                        .vegetarian(true)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Quinoa with Salad")
                        .calories(450.0)
                        .protein(40.0)
                        .fat(11.0)
                        .carbs(34.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Chicken with Whole Wheat Pasta with Spinach")
                        .calories(470.0)
                        .protein(42.0)
                        .fat(12.0)
                        .carbs(36.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Diet Pasta with Olive Oil")
                        .calories(440.0)
                        .protein(37.0)
                        .fat(13.0)
                        .carbs(38.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Egg Whites with Oats with Peanut Butter")
                        .calories(460.0)
                        .protein(34.0)
                        .fat(17.0)
                        .carbs(33.0)
                        .vegetarian(true)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Steak with Potato with Green Beans")
                        .calories(520.0)
                        .protein(46.0)
                        .fat(19.0)
                        .carbs(28.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Chicken with Oat Bread with Yogurt")
                        .calories(430.0)
                        .protein(38.0)
                        .fat(11.0)
                        .carbs(31.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Omelet with Light Cheese with Toast")
                        .calories(370.0)
                        .protein(29.0)
                        .fat(14.0)
                        .carbs(25.0)
                        .vegetarian(true)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Chickpeas with Veggies")
                        .calories(440.0)
                        .protein(36.0)
                        .fat(12.0)
                        .carbs(32.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Homemade Chicken Burger with Lettuce with Brown Bread")
                        .calories(460.0)
                        .protein(40.0)
                        .fat(14.0)
                        .carbs(30.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Chicken with Mushrooms with Brown Rice")
                        .calories(480.0)
                        .protein(42.0)
                        .fat(13.0)
                        .carbs(33.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Hummus Salad with Brown Bread")
                        .calories(430.0)
                        .protein(37.0)
                        .fat(11.0)
                        .carbs(29.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Oat Toast with Tomato")
                        .calories(410.0)
                        .protein(33.0)
                        .fat(14.0)
                        .carbs(28.0)
                        .vegetarian(true)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Lentil Soup with 2 Eggs with Toast")
                        .calories(450.0)
                        .protein(35.0)
                        .fat(13.0)
                        .carbs(34.0)
                        .vegetarian(true)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Zucchini with Whole Wheat Pasta")
                        .calories(470.0)
                        .protein(39.0)
                        .fat(12.0)
                        .carbs(37.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Fish with Salad with Half Potato")
                        .calories(440.0)
                        .protein(38.0)
                        .fat(14.0)
                        .carbs(26.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Chicken with Brown Toast with Yogurt")
                        .calories(460.0)
                        .protein(42.0)
                        .fat(12.0)
                        .carbs(29.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Mashed Potato with Broccoli")
                        .calories(450.0)
                        .protein(37.0)
                        .fat(10.0)
                        .carbs(34.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Chicken with Chickpeas with Salad")
                        .calories(470.0)
                        .protein(43.0)
                        .fat(13.0)
                        .carbs(32.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Steak with Roasted Veggies with Potato")
                        .calories(520.0)
                        .protein(45.0)
                        .fat(18.0)
                        .carbs(33.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("2 Eggs with Brown Bread with Arugula")
                        .calories(390.0)
                        .protein(28.0)
                        .fat(14.0)
                        .carbs(30.0)
                        .vegetarian(true)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Quinoa with Tomato")
                        .calories(440.0)
                        .protein(39.0)
                        .fat(11.0)
                        .carbs(31.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Chicken with Pasta with Veggies")
                        .calories(480.0)
                        .protein(44.0)
                        .fat(13.0)
                        .carbs(36.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Brown Bread with Cucumber with Yogurt")
                        .calories(430.0)
                        .protein(36.0)
                        .fat(12.0)
                        .carbs(28.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Peanut Butter with Toast")
                        .calories(460.0)
                        .protein(32.0)
                        .fat(16.0)
                        .carbs(30.0)
                        .vegetarian(true)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Salmon with Veggies with Potato")
                        .calories(510.0)
                        .protein(38.0)
                        .fat(20.0)
                        .carbs(33.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Brown Bread with Veggies")
                        .calories(400.0)
                        .protein(30.0)
                        .fat(13.0)
                        .carbs(26.0)
                        .vegetarian(true)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Brown Rice with Spinach")
                        .calories(470.0)
                        .protein(40.0)
                        .fat(11.0)
                        .carbs(34.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Cottage Cheese with Brown Bread with Cucumber")
                        .calories(390.0)
                        .protein(32.0)
                        .fat(12.0)
                        .carbs(27.0)
                        .vegetarian(true)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Chicken with Pasta with Salad")
                        .calories(470.0)
                        .protein(42.0)
                        .fat(11.0)
                        .carbs(35.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Chickpeas with Arugula")
                        .calories(440.0)
                        .protein(36.0)
                        .fat(13.0)
                        .carbs(32.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Steak with Beans with Half Brown Bread")
                        .calories(480.0)
                        .protein(42.0)
                        .fat(14.0)
                        .carbs(28.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Veggie Soup with Chicken with Toast")
                        .calories(430.0)
                        .protein(38.0)
                        .fat(10.0)
                        .carbs(29.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Egg Whites with Toast with Light Labneh")
                        .calories(370.0)
                        .protein(30.0)
                        .fat(11.0)
                        .carbs(25.0)
                        .vegetarian(true)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Lean Beef Burger with Potato with Lettuce")
                        .calories(460.0)
                        .protein(38.0)
                        .fat(13.0)
                        .carbs(33.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Shrimp with Spinach with Brown Rice")
                        .calories(470.0)
                        .protein(41.0)
                        .fat(11.0)
                        .carbs(32.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Eggs with Oats with Banana")
                        .calories(450.0)
                        .protein(30.0)
                        .fat(13.0)
                        .carbs(35.0)
                        .vegetarian(true)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Tuna with Whole Wheat Pasta with Peppers")
                        .calories(460.0)
                        .protein(37.0)
                        .fat(12.0)
                        .carbs(36.0)
                        .vegetarian(false)
                        .type(MealType.DINNER)
                        .build(),

                FoodItem.builder()
                        .name("Greek Yogurt (150g)")
                        .calories(100.0)
                        .protein(17.0)
                        .fat(0.7)
                        .carbs(6.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Boiled Egg (1 medium)")
                        .calories(78.0)
                        .protein(6.3)
                        .fat(5.3)
                        .carbs(0.6)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Cottage Cheese (100g)")
                        .calories(98.0)
                        .protein(11.1)
                        .fat(4.3)
                        .carbs(3.4)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Protein Bar (generic 50g)")
                        .calories(200.0)
                        .protein(20.0)
                        .fat(6.0)
                        .carbs(18.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Roasted Chickpeas (50g)")
                        .calories(180.0)
                        .protein(10.0)
                        .fat(4.0)
                        .carbs(22.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Peanut Butter on Brown Toast")
                        .calories(210.0)
                        .protein(9.0)
                        .fat(12.0)
                        .carbs(16.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Labneh Light with Cucumber (100g)")
                        .calories(120.0)
                        .protein(10.0)
                        .fat(5.0)
                        .carbs(4.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Oats with Banana and Peanut Butter")
                        .calories(320.0)
                        .protein(9.0)
                        .fat(14.0)
                        .carbs(38.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Brown Toast with Peanut Butter and Flaxseed")
                        .calories(300.0)
                        .protein(10.0)
                        .fat(18.0)
                        .carbs(28.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Greek Yogurt with Seasonal Fruits")
                        .calories(200.0)
                        .protein(12.0)
                        .fat(5.0)
                        .carbs(20.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Banana Smoothie with Oats and Skim Milk")
                        .calories(250.0)
                        .protein(8.0)
                        .fat(6.0)
                        .carbs(35.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Boiled Eggs with Tomato and Cucumber")
                        .calories(220.0)
                        .protein(12.0)
                        .fat(10.0)
                        .carbs(2.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Avocado Toast with Olive Oil")
                        .calories(270.0)
                        .protein(6.0)
                        .fat(14.0)
                        .carbs(30.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Fruit Yogurt with Grated Coconut")
                        .calories(260.0)
                        .protein(6.0)
                        .fat(14.0)
                        .carbs(24.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Cottage Cheese with Pineapple Slices")
                        .calories(190.0)
                        .protein(14.0)
                        .fat(4.0)
                        .carbs(18.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Whole Wheat Pancakes with Honey")
                        .calories(280.0)
                        .protein(8.0)
                        .fat(6.0)
                        .carbs(42.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Oats with Milk, Apple, and Walnuts")
                        .calories(310.0)
                        .protein(7.0)
                        .fat(12.0)
                        .carbs(35.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Baked Potato Patties with Lettuce and Tomato")
                        .calories(300.0)
                        .protein(8.0)
                        .fat(10.0)
                        .carbs(28.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Spinach and Mushroom Wrap")
                        .calories(270.0)
                        .protein(9.0)
                        .fat(12.0)
                        .carbs(25.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Hummus and Vegetable Sandwich")
                        .calories(290.0)
                        .protein(10.0)
                        .fat(11.0)
                        .carbs(30.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Brown Rice Porridge with Almonds")
                        .calories(260.0)
                        .protein(9.0)
                        .fat(8.0)
                        .carbs(34.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Cornflakes with Low-Fat Milk")
                        .calories(280.0)
                        .protein(7.0)
                        .fat(10.0)
                        .carbs(38.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Tofu and Spinach Toast")
                        .calories(230.0)
                        .protein(12.0)
                        .fat(8.0)
                        .carbs(20.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Brown Bread with Peanut Butter and Dates")
                        .calories(310.0)
                        .protein(8.0)
                        .fat(13.0)
                        .carbs(35.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Fava Beans Sandwich with Spicy Oil and Tomato")
                        .calories(330.0)
                        .protein(10.0)
                        .fat(12.0)
                        .carbs(35.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Baked Potato and Zucchini Omelet")
                        .calories(290.0)
                        .protein(10.0)
                        .fat(14.0)
                        .carbs(25.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Baked Sweet Potato with Tahini")
                        .calories(240.0)
                        .protein(6.0)
                        .fat(10.0)
                        .carbs(32.0)
                        .vegetarian(true)
                        .type(MealType.BREAKFAST)
                        .build(),

                FoodItem.builder()
                        .name("Apple with Peanut Butter")
                        .calories(200.0)
                        .protein(4.0)
                        .fat(10.0)
                        .carbs(22.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Banana with Almonds")
                        .calories(180.0)
                        .protein(3.0)
                        .fat(8.0)
                        .carbs(25.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Dates with Walnuts")
                        .calories(190.0)
                        .protein(2.0)
                        .fat(7.0)
                        .carbs(28.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Mixed Nuts (30g)")
                        .calories(170.0)
                        .protein(5.0)
                        .fat(14.0)
                        .carbs(6.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Carrot and Cucumber with Hummus")
                        .calories(120.0)
                        .protein(4.0)
                        .fat(7.0)
                        .carbs(10.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Brown Toast with Avocado")
                        .calories(140.0)
                        .protein(3.0)
                        .fat(6.0)
                        .carbs(18.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Greek Yogurt with a Spoon of Honey")
                        .calories(160.0)
                        .protein(8.0)
                        .fat(8.0)
                        .carbs(12.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Date and Coconut Balls")
                        .calories(180.0)
                        .protein(4.0)
                        .fat(10.0)
                        .carbs(15.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Grapes with Cottage Cheese")
                        .calories(150.0)
                        .protein(6.0)
                        .fat(5.0)
                        .carbs(20.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Dried Figs with Cashews")
                        .calories(170.0)
                        .protein(3.0)
                        .fat(9.0)
                        .carbs(22.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Zucchini Lasagna with Vegan Cheese")
                        .calories(350.0)
                        .protein(18.0)
                        .fat(12.0)
                        .carbs(30.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Stuffed Eggplant with Rice & Herbs")
                        .calories(400.0)
                        .protein(12.0)
                        .fat(10.0)
                        .carbs(45.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Roasted Cauliflower Tacos with Tahini Sauce")
                        .calories(320.0)
                        .protein(10.0)
                        .fat(14.0)
                        .carbs(35.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Spinach Chickpea Curry with White or Brown Rice")
                        .calories(450.0)
                        .protein(20.0)
                        .fat(9.0)
                        .carbs(55.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Pasta with Vegan Cheese and Broccoli")
                        .calories(420.0)
                        .protein(22.0)
                        .fat(15.0)
                        .carbs(50.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Whole Wheat Pasta with Tomato & Basil Sauce")
                        .calories(400.0)
                        .protein(16.0)
                        .fat(8.0)
                        .carbs(60.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Pumpkin Lentil Soup with Garlic Toast")
                        .calories(330.0)
                        .protein(18.0)
                        .fat(7.0)
                        .carbs(35.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Stuffed Grape Leaves with Vegetables & Rice, Tahini Salad")
                        .calories(370.0)
                        .protein(14.0)
                        .fat(10.0)
                        .carbs(45.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Lentil Pie with Rice and Tomato")
                        .calories(380.0)
                        .protein(20.0)
                        .fat(12.0)
                        .carbs(40.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Pasta with Eggplant and Tomato Sauce")
                        .calories(400.0)
                        .protein(15.0)
                        .fat(10.0)
                        .carbs(55.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Brown Rice with Foul Medames & Cooked Vegetables")
                        .calories(450.0)
                        .protein(22.0)
                        .fat(9.0)
                        .carbs(60.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Cauliflower & Peas Curry with White Rice")
                        .calories(430.0)
                        .protein(18.0)
                        .fat(11.0)
                        .carbs(55.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Mujaddara (Lentils, Rice & Caramelized Onions)")
                        .calories(420.0)
                        .protein(20.0)
                        .fat(7.0)
                        .carbs(60.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grain Bowl with Roasted Vegetables (Rice/Couscous/Bulgur)")
                        .calories(400.0)
                        .protein(15.0)
                        .fat(10.0)
                        .carbs(55.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Lentil or Foul Burger with Baladi Bread and Salad")
                        .calories(450.0)
                        .protein(22.0)
                        .fat(15.0)
                        .carbs(45.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Sweet Potato & Kale Bowl with Tahini Sauce")
                        .calories(400.0)
                        .protein(14.0)
                        .fat(14.0)
                        .carbs(50.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Foul Tacos with Tortilla and Tahini Sauce")
                        .calories(430.0)
                        .protein(20.0)
                        .fat(14.0)
                        .carbs(50.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Mixed Rice with Vegetables (White Rice with Mixed Veg)")
                        .calories(420.0)
                        .protein(15.0)
                        .fat(10.0)
                        .carbs(60.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Pasta with Mushroom-Tomato Sauce or Vegetable Pasta")
                        .calories(400.0)
                        .protein(16.0)
                        .fat(12.0)
                        .carbs(55.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Vegan Chili (Beans, Tomato, Chili Pepper) with White Rice")
                        .calories(440.0)
                        .protein(22.0)
                        .fat(10.0)
                        .carbs(55.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Zucchini Boats Stuffed with Rice and Vegetables")
                        .calories(380.0)
                        .protein(14.0)
                        .fat(9.0)
                        .carbs(45.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Simple Lentil or Foul Burger with Baladi Bread and Salad")
                        .calories(430.0)
                        .protein(20.0)
                        .fat(13.0)
                        .carbs(45.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Noodles with Peanut Sauce (or local peanuts/tahini sauce)")
                        .calories(420.0)
                        .protein(18.0)
                        .fat(14.0)
                        .carbs(55.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Grilled Vegetable Tagine with Chickpeas and Brown Rice")
                        .calories(530.0)
                        .protein(18.5)
                        .fat(12.5)
                        .carbs(85.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Vegan Gnocchi (Boiled Potato Pasta with Tomato Sauce)")
                        .calories(400.0)
                        .protein(15.0)
                        .fat(10.0)
                        .carbs(60.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Buddha Bowl with Roasted Sweet Potato and Green Salad")
                        .calories(420.0)
                        .protein(14.0)
                        .fat(12.0)
                        .carbs(55.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name(" Chickpeas with Tomato & Spices")
                        .calories(410.0)
                        .protein(18.0)
                        .fat(10.0)
                        .carbs(50.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Vegetable Noodle Bowl (or Thin Pasta)")
                        .calories(390.0)
                        .protein(16.0)
                        .fat(9.0)
                        .carbs(50.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("White Rice with Lentils and White Beans Cooked with Tomato and Spices")
                        .calories(440.0)
                        .protein(22.0)
                        .fat(10.0)
                        .carbs(60.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Spiced Roasted Vegetable Tagine with Boiled Chickpeas ")
                        .calories(420.0)
                        .protein(15.0)
                        .fat(10.0)
                        .carbs(50.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Stir-fried Eggplant with Pasta or Rice")
                        .calories(400.0)
                        .protein(15.0)
                        .fat(12.0)
                        .carbs(50.0)
                        .vegetarian(true)
                        .type(MealType.LUNCH)
                        .build(),

                FoodItem.builder()
                        .name("Apple Slices with Peanut Butter")
                        .calories(180.0)
                        .protein(4.0)
                        .fat(9.0)
                        .carbs(22.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Plant-Based Yogurt with Berries")
                        .calories(120.0)
                        .protein(6.0)
                        .fat(4.0)
                        .carbs(14.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Almonds (≈ 20g)")
                        .calories(140.0)
                        .protein(5.0)
                        .fat(12.0)
                        .carbs(5.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Avocado on Whole Grain Cracker")
                        .calories(160.0)
                        .protein(3.0)
                        .fat(11.0)
                        .carbs(12.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Dates with Almond Butter")
                        .calories(180.0)
                        .protein(3.0)
                        .fat(8.0)
                        .carbs(24.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Trail Mix (nuts + raisins)")
                        .calories(200.0)
                        .protein(5.0)
                        .fat(12.0)
                        .carbs(18.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Baked Sweet Potato Slices")
                        .calories(110.0)
                        .protein(2.0)
                        .fat(2.0)
                        .carbs(23.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Cucumber Slices with Hummus")
                        .calories(100.0)
                        .protein(3.0)
                        .fat(6.0)
                        .carbs(8.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Dark Chocolate (2 squares) + Walnuts")
                        .calories(190.0)
                        .protein(3.0)
                        .fat(14.0)
                        .carbs(12.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build(),

                FoodItem.builder()
                        .name("Vegan Smoothie (Banana + Spinach + Almond Milk)")
                        .calories(180.0)
                        .protein(5.0)
                        .fat(6.0)
                        .carbs(25.0)
                        .vegetarian(true)
                        .type(MealType.SNACK)
                        .build()

        );

        foodItemRepository.saveAll(foodItems);
        log.info("✅ Seeded " + foodItems.size() + " food items.");
    }
    private BodyFocus getOrCreateBodyFocus(String name, BodyFocusRepository repo) {
        return repo.findByNameIgnoreCase(name).orElseGet(() -> repo.save(BodyFocus.builder().name(name).build()));
    }

    private Exercise buildExercise(String name, String desc, Integer sets, String reps, Integer rest, Integer cal, Integer totalCal, Set<BodyFocus> focuses, Integer duration) {
        return Exercise.builder()
                .name(name).description(desc).sets(sets).reps(reps).durationRestSeconds(rest)
                .caloriesBurned(cal).totalCalories(totalCal).durationSeconds(duration)
                .bodyFocuses(focuses).build();
    }

}
