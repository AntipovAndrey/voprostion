package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.dto.QuestionDto;
import ru.voprostion.app.domain.model.Question;

public interface QuestionDetailsUseCase {

    Question getDetailed(QuestionDto questionDto);
}
