package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.dto.QuestionDto;

public interface QuestionDetailsUseCase {

    QuestionDto getDetailed(Long questionId);
}
