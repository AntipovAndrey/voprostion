package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.model.Answer;

import java.util.List;

public interface AnswersListUseCase {

    List<Answer> getAll(Long questionId);
}
