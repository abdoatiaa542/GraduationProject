package com.abdoatiia542.GraduationProject.model.embeddables;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BodyFatRange {

    @NotNull
    @Min(5)
    @Max(42)
    private Double min;

    @Min(5)
    private Double max;
}
