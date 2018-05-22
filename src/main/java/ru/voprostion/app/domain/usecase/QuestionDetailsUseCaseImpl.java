package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.dto.QuestionDto;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.service.QuestionService;

@Service
public class QuestionDetailsUseCaseImpl implements QuestionDetailsUseCase {

    private QuestionService questionService;

    @Autowired
    public QuestionDetailsUseCaseImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Question getDetailed(Long questionId) {
        return questionService.findById(questionId);
    }
}
