package ru.voprostion.app.domain.dto;

import lombok.Data;

@Data
public class AnswerDto extends BaseDto {
    private QuestionDto questionDto;
    private String answer;
}
