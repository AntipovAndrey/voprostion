package ru.voprostion.app.domain.dto;

import lombok.Data;

@Data
public class AnswerDto extends BaseDto {
    private Long questionId;
    private int rating;
    private UserDto userDto;
    private String answer;
}
