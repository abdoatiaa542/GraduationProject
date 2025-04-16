//package com.abdoatiia542.GraduationProject.model;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.*;
//
//@Entity
//@Table(name = "Exercises")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Exercise {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE})
//    @JoinColumn(name = "Workout_ID", nullable = false)
//    private Workout workout;
//
//    @Column(name = "Exercise_Name")
//    @NotNull(message = " Exercise name is required")
//    private String exerciseName;
//
//    @Column(name = "Exercise_Image")
//    private String exerciseImage;
//
//    @Column(name = "Sets")
//    private Integer sets;
//
//    @Column(name = "Reps")
//    private Integer reps;
//
//    @Column(name = "Time_Duration")
//    private Double timeDuration;
//
//    @Column(name = "Rest_Time")
//    private Double restTime;
//
//    @Column(name = "Weight", precision = 5, scale = 2)
//    private Double weight;
//}
