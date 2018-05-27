package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.dto.QuestionPreviewDto;

import java.util.List;

public interface QuestionsListUseCase {

    List<QuestionPreviewDto> getAll();

    List<QuestionPreviewDto> getByUser(String username);

    List<QuestionPreviewDto> getByTag(String tag);
}
