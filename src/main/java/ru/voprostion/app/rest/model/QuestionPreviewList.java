package ru.voprostion.app.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.voprostion.app.domain.dto.QuestionPreviewDto;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
public class QuestionPreviewList {
    private final Iterable<QuestionPreviewRest> questions;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String next;

    public QuestionPreviewList(Iterable<QuestionPreviewDto> questionPreviews) {
        Objects.requireNonNull(questionPreviews);
        questions = StreamSupport.stream(questionPreviews.spliterator(), false)
                .map(QuestionPreviewRest::new)
                .collect(Collectors.toList());
    }
}
