package ru.voprostion.app.domain.dto;

import lombok.Data;
import ru.voprostion.app.domain.model.Answer;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Tag;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class QuestionDto extends BaseDto {
    private String question;
    private List<TagDto> tags;
    private List<AnswerDto> answers;
    private UserDto user;

    public QuestionDto(Question question) {
        setId(question.getId());
        this.question = question.getQuestionTitle();
        user = new UserDto(question.getUser());
        tags = question.getTags()
                .stream()
                .sorted(Comparator.comparing(Tag::getTagName))
                .map(TagDto::new)
                .collect(Collectors.toList());
        answers = question.getAnswers()
                .stream()
                .sorted(answersByDate())
                .map(AnswerDto::new)
                .collect(Collectors.toList());
    }

    private Comparator<Answer> answersByDate() {
        return Comparator.comparing(Answer::getRating, Comparator.reverseOrder())
                .thenComparing(Answer::getDateCreated, Comparator.reverseOrder());
    }
}
