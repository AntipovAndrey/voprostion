package ru.voprostion.app.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import ru.voprostion.app.domain.model.Question;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonPropertyOrder({"id", "user", "tags", "question", "answers"})
public class QuestionDto extends BaseDto {
    private String question;
    private UserDto user;
    @JsonIgnore
    private List<TagDto> tags;
    private List<AnswerDto> answers;

    @JsonProperty("tags")
    public List<String> getTagsAsString() {
        return tags.stream()
                .map(TagDto::getName)
                .collect(Collectors.toList());
    }

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
