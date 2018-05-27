package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.dto.QuestionDto;

import java.util.List;

public interface AskQuestionUseCase {

    QuestionDto ask(String question, List<String> tags);

    boolean canAsk();
}
