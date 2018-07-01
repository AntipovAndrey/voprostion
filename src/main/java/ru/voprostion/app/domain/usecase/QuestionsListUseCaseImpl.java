package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.dto.QuestionPreviewDto;
import ru.voprostion.app.domain.model.Tag;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.domain.service.QuestionService;
import ru.voprostion.app.domain.service.TagService;
import ru.voprostion.app.domain.service.UserService;
import ru.voprostion.app.domain.usecase.exception.TagNotFoundException;

@Service
public class QuestionsListUseCaseImpl implements QuestionsListUseCase {

    private final QuestionService questionService;
    private final UserService userService;
    private final TagService tagService;
    private final String sortingKey;

    @Autowired
    public QuestionsListUseCaseImpl(QuestionService questionService,
                                    UserService userService,
                                    TagService tagService,
                                    @Value("${question.sortingKey}") String sortingKey) {
        this.questionService = questionService;
        this.userService = userService;
        this.tagService = tagService;
        this.sortingKey = sortingKey;
    }

    @Override
    public Iterable<QuestionPreviewDto> getAll(int pageNumber) {
        return questionService.getByPage(pageNumber, "dateCreated")
                .map(QuestionPreviewDto::new);
    }

    @Override
    public Iterable<QuestionPreviewDto> getByUser(String username, int pageNumber) {
        final User user = userService.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return questionService.getByUser(user, pageNumber, sortingKey)
                .map(QuestionPreviewDto::new);
    }

    @Override
    public Iterable<QuestionPreviewDto> getByTag(String tagName, int pageNumber) {
        final Tag tag = tagService.findByName(tagName)
                .orElseThrow(() -> new TagNotFoundException(tagName));
        return questionService.getByTag(tag, pageNumber, sortingKey)
                .map(QuestionPreviewDto::new);
    }

}
