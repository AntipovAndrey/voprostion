package ru.voprostion.app.domain.usecase;

import java.util.List;

public interface AskQuestionUseCase {

    Long ask(String question, List<String> tags);

    boolean canAsk();
}
