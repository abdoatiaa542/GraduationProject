//package com.abdoatiia542.GraduationProject.model;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "workout_category")
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//public class WorkoutCategory {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    @NotNull(message = " Name is required")
//    private String name;
//    @Column(nullable = false)
//    private String description;
//    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//private Set<Workout> workouts=new HashSet<>();
//}
