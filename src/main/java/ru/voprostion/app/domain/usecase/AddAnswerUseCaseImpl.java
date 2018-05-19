package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.model.Answer;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.domain.service.AnswerService;
import ru.voprostion.app.domain.service.QuestionService;
import ru.voprostion.app.domain.service.UserService;

@Service
public class AddAnswerUseCaseImpl implements AddAnswerUseCase {

    private AnswerService answerService;
    private UserService userService;
    private QuestionDetailsUseCase questionDetailsUseCase;
    private QuestionService questionService;

    @Autowired
    public AddAnswerUseCaseImpl(AnswerService answerService,
                                UserService userService,
                                QuestionDetailsUseCase questionDetailsUseCase,
                                QuestionService questionService) {
        this.answerService = answerService;
        this.answerService = answerService;
        this.userService = userService;
        this.questionDetailsUseCase = questionDetailsUseCase;
        this.questionService = questionService;
    }

    @Override
    public Answer answer(Long questionId, String answerText) {
        final Question question = questionDetailsUseCase.getDetailed(questionId);
        final User loggedIn = userService.getLoggedIn();
        Answer answer = new Answer();
        answer.setAnswer(answerText);
        answer.setUser(loggedIn);
        question.addAnswer(answer);
        answerService.save(answer);
        return answer;
    }

    @Override
    public boolean canAnswer(Long questionId) {
        final User loggedIn = userService.getLoggedIn();
        if (loggedIn == null) return false;
        final Question question = questionService.findById(questionId);
        if (question.getUser().equals(loggedIn)) return false;
        if (answerService.findPreviousAnswer(question, loggedIn) != null) return false;
        return true;
    }
}
