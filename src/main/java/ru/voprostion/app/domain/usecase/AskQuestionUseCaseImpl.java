package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Tag;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.domain.service.QuestionService;
import ru.voprostion.app.domain.service.UserService;
import ru.voprostion.app.domain.usecase.exception.TooFewTagsException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AskQuestionUseCaseImpl implements AskQuestionUseCase {

    private QuestionService questionService;
    private UserService userService;

    @Autowired
    public AskQuestionUseCaseImpl(QuestionService questionService,
                                  UserService userService) {
        this.questionService = questionService;
        this.userService = userService;
    }

    @Override
    public Long ask(String question, List<String> tags) {
        if (!canAsk()) return null;
        if (tags.size() < 2) throw new TooFewTagsException("Too few tags : " + tags.size());
        final Question model = questionService.create(question, tags.stream()
                .map(Tag::new)
                .collect(Collectors.toList()));
        return model.getId();
    }

    @Override
    public boolean canAsk() {
        final User loggedIn = userService.getLoggedIn();
        if (loggedIn == null) return false;
        return true;
    }
}
