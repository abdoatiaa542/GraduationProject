package com.abdoatiia542.GraduationProject.model.plan;


import jakarta.persistence.*;
import lombok.*;

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

}