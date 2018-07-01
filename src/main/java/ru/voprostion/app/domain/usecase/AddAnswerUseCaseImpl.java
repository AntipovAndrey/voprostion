package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.model.Answer;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.domain.service.AnswerService;
import ru.voprostion.app.domain.service.QuestionService;
import ru.voprostion.app.domain.service.UserService;
import ru.voprostion.app.domain.usecase.exception.IllegalAccessException;
import ru.voprostion.app.domain.usecase.exception.QuestionNotFoundException;

import java.util.Objects;

@Service
public class AddAnswerUseCaseImpl implements AddAnswerUseCase {

    private final AnswerService answerService;
    private final UserService userService;
    private final QuestionService questionService;

    @Autowired
    public AddAnswerUseCaseImpl(AnswerService answerService,
                                UserService userService,
                                QuestionService questionService) {
        this.answerService = answerService;
        this.userService = userService;
        this.questionService = questionService;
    }

    @Override
    public Long answer(Long questionId, String answerText) {
        final Question question = questionService.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(Objects.toString(questionId)));
        final User loggedIn = userService.getLoggedIn()
                .orElseThrow(IllegalAccessException::new);
        Answer answer = new Answer();
        answer.setAnswer(answerText);
        answer.setUser(loggedIn);
        question.addAnswer(answer);
        answerService.save(answer);
        return answer.getId();
    }

    @Override
    public boolean canAnswer(Long questionId) {
        if (!userService.getLoggedIn().isPresent()) return false;
        final Question question = questionService.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(Objects.toString(questionId)));
        final User loggedIn = userService.getLoggedIn().get();
        if (question.getUser().equals(loggedIn)) return false;
        if (answerService.findPreviousAnswer(question, loggedIn).isPresent()) return false;
        return true;
    }
}
