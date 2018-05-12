package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.dto.QuestionDto;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Tag;
import ru.voprostion.app.domain.service.QuestionService;

@Service
public class AskQuestionUseCaseImpl implements AskQuestionUseCase {

    private QuestionService questionService;

    @Autowired
    public AskQuestionUseCaseImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Question ask(QuestionDto questionDto) {
        return questionService.create(questionDto.getQuestion(),
                Tag.fromString(questionDto.getTags()));
    }
}
