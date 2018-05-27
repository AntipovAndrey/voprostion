package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.dto.AnswerDto;

public interface AddAnswerUseCase {

    AnswerDto answer(Long questionId, String answer);

    boolean canAnswer(Long questionId);
}
