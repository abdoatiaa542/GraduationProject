package com.abdoatiia542.GraduationProject.model.workout;

import com.abdoatiia542.GraduationProject.model.BaseAttachment;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exercise_attachments")
public class ExerciseAttachment extends BaseAttachment {
    @OneToOne
    @JoinColumn(nullable = false, updatable = false)
    private Exercise exercise;
}
