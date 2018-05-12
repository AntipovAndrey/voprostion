package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.dto.AnswerDto;
import ru.voprostion.app.domain.model.Answer;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.domain.service.AnswerService;
import ru.voprostion.app.domain.service.UserService;

@Service
public class AddAnswerUseCaseImpl implements AddAnswerUseCase {

    private AnswerService answerService;
    private UserService userService;
    private QuestionDetailsUseCase questionDetailsUseCase;

    @Autowired
    public AddAnswerUseCaseImpl(AnswerService answerService,
                                UserService userService,
                                QuestionDetailsUseCase questionDetailsUseCase) {
        this.answerService = answerService;
        this.answerService = answerService;
        this.userService = userService;
        this.questionDetailsUseCase = questionDetailsUseCase;
    }

    @Override
    public Answer answer(Long questionId, AnswerDto answerDto) {
        final Question question = questionDetailsUseCase.getById(questionId);
        final User loggedIn = userService.getLoggedIn();
        if (loggedIn.equals(question.getUser())) {
            throw new RuntimeException("you can not answer your question");
        }
        Answer answer = new Answer();
        answer.setAnswer(answerDto.getAnswer());
        answer.setUser(loggedIn);
        question.addAnswer(answer);
        answerService.save(answer);
        return answer;
    }
}
