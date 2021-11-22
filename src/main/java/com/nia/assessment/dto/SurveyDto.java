package com.nia.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyDto {

    @NotNull
    private Long patientId;

    @Min(0)
    @Max(10)
    private short sleepGrade;

    @Min(0)
    @Max(10)
    private short skinGrade;

}
