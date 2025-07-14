package com.abdoatiia542.GraduationProject.model.exercises;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "body_focus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BodyFocus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "bodyFocuses")
    private Set<Exercise> exercises;

}