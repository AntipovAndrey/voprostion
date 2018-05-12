package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.service.QuestionService;

import java.util.List;

@Service
public class QuestionsListUseCaseImpl implements QuestionsListUseCase {

    private QuestionService questionService;

    @Autowired
    public QuestionsListUseCaseImpl(QuestionService questionService) {
        this.questionService = questionService;
    }


    @Override
    public List<Question> getAll() {
        return questionService.getAll();
    }
}
