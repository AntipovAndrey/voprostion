package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.model.Question;

import java.util.List;

public interface QuestionsListUseCase {

    List<Question> getAll();

    List<Question> getByUser(String username);

    List<Question> getByTag(String tag);
}
