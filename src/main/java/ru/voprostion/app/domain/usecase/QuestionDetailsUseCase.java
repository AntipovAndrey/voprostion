package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.dto.QuestionDto;

import java.util.Optional;

public interface QuestionDetailsUseCase {

    Optional<QuestionDto> getDetailed(Long questionId);
}
