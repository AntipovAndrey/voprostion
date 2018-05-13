package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.dto.QuestionDto;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Tag;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.domain.service.AnswerService;
import ru.voprostion.app.domain.service.QuestionService;
import ru.voprostion.app.domain.service.UserService;

@Service
public class AskQuestionUseCaseImpl implements AskQuestionUseCase {

    private QuestionService questionService;
    private AnswerService answerService;
    private UserService userService;

    @Autowired
    public AskQuestionUseCaseImpl(QuestionService questionService,
                                  AnswerService answerService,
                                  UserService userService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.userService = userService;
    }

    @Override
    public Question ask(QuestionDto questionDto) {
        if (!canAsk(Long.valueOf(questionDto.getQuestion()))) return null;
        return questionService.create(questionDto.getQuestion(),
                Tag.fromString(questionDto.getTags()));
    }

    @Override
    public boolean canAsk(Long questionId) {
        final User loggedIn = userService.getLoggedIn();
        if (loggedIn == null) return false;
        final Question question = questionService.findById(questionId);
        if (question.getUser().equals(loggedIn)) return false;
        if (answerService.findPreviousAnswer(question, loggedIn) != null) return false;
        return true;
    }
}
