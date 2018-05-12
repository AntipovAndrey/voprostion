package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.model.Question;

public interface QuestionDetailsUseCase {

    Question getById(Long id);
}
