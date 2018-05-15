package ru.voprostion.app.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class QuestionDto extends BaseDto {
    @NotNull
    @Length(min = 5)
    private String question;
    @NotNull
    @Length(min = 2)
    private String tags;
}
