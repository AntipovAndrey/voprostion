package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.dto.QuestionPreviewDto;

import java.util.List;

public interface QuestionsListUseCase {

    List<QuestionPreviewDto> getAll(long startFrom);

    Iterable<QuestionPreviewDto> getByUser(String username, int pageNumber);

    Iterable<QuestionPreviewDto> getByTag(String tag, int pageNumber);
}
