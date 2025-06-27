package com.abdoatiia542.GraduationProject.model.embeddables;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BodyFatRange {

    @NotNull
    @Min(5)
    @Max(50)
    private Double min;

    @NotNull
    @Min(5)
    @Max(50)
    private Double max;
}
