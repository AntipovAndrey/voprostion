package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.dto.QuestionPreviewDto;

public interface QuestionsListUseCase {

    Iterable<QuestionPreviewDto> getAll(int pageNumber);

    Iterable<QuestionPreviewDto> getByUser(String username, int pageNumber);

    Iterable<QuestionPreviewDto> getByTag(String tag, int pageNumber);
}
