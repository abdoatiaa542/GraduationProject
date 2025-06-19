//package com.abdoatiia542.GraduationProject.model;
//
//import com.abdoatiia542.GraduationProject.model.enumerations.DifficultyLevel;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "workouts")
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//public class Workout {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE})
//    @JoinColumn(name = "Trainee_ID")
//    private User trainee;
//
//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE})
//    @JoinColumn(name = "Category_ID", nullable = false)
//    private WorkoutCategory category;
//    @Column(name = "Workout_Name", nullable = false)
//    @NotNull(message = " Workout name is required")
//    private String workoutName;
//
//    @Column(name = "Workout_Image")
//    private String workoutImage;
//
//    @Column(name = "Duration_In_Minutes", nullable = false)
//    private Double durationInMinutes;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "Difficulty_Level")
//    private DifficultyLevel difficultyLevel;
//
//    @Column(name = "Created_At")
//    private LocalDateTime createdAt;
//@OneToMany(mappedBy = "workout", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private Set<Exercise> exercises = new HashSet<>();
//
//}