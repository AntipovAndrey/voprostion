package ru.voprostion.app.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import ru.voprostion.app.domain.dto.QuestionPreviewDto;

import java.util.Objects;

@Data
public class QuestionPreviewRest {
    @JsonUnwrapped
    private final QuestionPreviewDto question;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String details;

    public QuestionPreviewRest(QuestionPreviewDto questionPreview) {
        this.question = Objects.requireNonNull(questionPreview);
    }
}
