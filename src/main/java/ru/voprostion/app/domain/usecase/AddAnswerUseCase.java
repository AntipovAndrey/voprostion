package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.dto.AnswerDto;
import ru.voprostion.app.domain.model.Answer;

public interface AddAnswerUseCase {

    Answer answer(Long questionId, AnswerDto answerDto);
}
