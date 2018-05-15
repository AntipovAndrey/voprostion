package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.dto.QuestionDto;
import ru.voprostion.app.domain.model.Question;

public interface AskQuestionUseCase {

    Question ask(QuestionDto questionDto);

    boolean canAsk(QuestionDto questionDto);
}
