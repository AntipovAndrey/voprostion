package ru.voprostion.app.domain.dto;

import lombok.Data;
import ru.voprostion.app.domain.model.Question;

import java.util.List;

@Data
public class QuestionDto extends BaseDto {
    private String question;
    private List<TagDto> tags;
    private List<AnswerDto> answers;
    private UserDto user;

    public QuestionDto(Question question, List<AnswerDto> answers, List<TagDto> tags) {
        initWithoutCollections(question);
        this.tags = tags;
        this.answers = answers;
    }

    private void initWithoutCollections(Question question) {
        setId(question.getId());
        this.question = question.getQuestionTitle();
        user = new UserDto(question.getUser());
    }
}
