package ru.voprostion.app.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Tag;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonPropertyOrder({"id", "user", "tags", "question", "answers"})
public class QuestionPreviewDto extends BaseDto {
    private String question;
    private List<TagDto> tags;
    private int answers;
    private UserDto user;

    @JsonProperty("tags")
    public List<String> getTagsAsString() {
        return tags.stream()
                .map(TagDto::getName)
                .collect(Collectors.toList());
    }

    public QuestionPreviewDto(Question question) {
        setId(question.getId());
        this.question = question.getQuestionTitle();
        user = new UserDto(question.getUser());
        answers = question.getAnswers().size();
        tags = question.getTags()
                .stream()
                .sorted(Comparator.comparing(Tag::getTagName))
                .map(TagDto::new)
                .collect(Collectors.toList());
    }
}
