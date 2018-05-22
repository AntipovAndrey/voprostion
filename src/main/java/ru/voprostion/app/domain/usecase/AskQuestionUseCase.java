package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.model.Question;

import java.util.List;

public interface AskQuestionUseCase {

    Question ask(String question, List<String> tags);

    boolean canAsk();
}
