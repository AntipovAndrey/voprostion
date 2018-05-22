package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Tag;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.domain.service.AnswerService;
import ru.voprostion.app.domain.service.QuestionService;
import ru.voprostion.app.domain.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

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
    public Question ask(String question, List<String> tags) {
        if (!canAsk()) return null;
        return questionService.create(question, tags.stream().map(Tag::new).collect(Collectors.toList()));
    }

    @Override
    public boolean canAsk() {
        final User loggedIn = userService.getLoggedIn();
        if (loggedIn == null) return false;
        return true;
    }
}
