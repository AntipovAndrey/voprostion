package ru.voprostion.app.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.voprostion.app.validation.ListSize;

import javax.validation.constraints.NotNull;

@Data
public class QuestionDto extends BaseDto {
    @NotNull
    @Length(min = 5, max = 140)
    private String question;
    @NotNull
    @Length(min = 2, max = 30)
    @ListSize(min = 2)
    private String tags;
    private UserDto userDto;
    private int answersCount;
}
