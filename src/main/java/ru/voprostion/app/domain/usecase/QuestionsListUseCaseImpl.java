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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionsListUseCaseImpl implements QuestionsListUseCase {

    private final QuestionService questionService;
    private final UserService userService;
    private final TagService tagService;
    private Integer pageSize;

    @Autowired
    public QuestionsListUseCaseImpl(QuestionService questionService,
                                    UserService userService,
                                    TagService tagService,
                                    @Value("${question.pageSize}") Integer pageSize) {
        this.questionService = questionService;
        this.userService = userService;
        this.tagService = tagService;
        this.pageSize = pageSize;
    }

    @Override
    public List<QuestionPreviewDto> getAll(long from) {
        return questionService.getBeforeDate(new Date(from), pageSize)
                .stream()
                .map(QuestionPreviewDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<QuestionPreviewDto> getByUser(String username, int pageNumber) {
        final User user = userService.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return questionService.getByUser(user, pageNumber, pageSize)
                .map(QuestionPreviewDto::new);
    }

    @Override
    public Iterable<QuestionPreviewDto> getByTag(String tagName, int pageNumber) {
        final Tag tag = tagService.findByName(tagName)
                .orElseThrow(() -> new TagNotFoundException(tagName));
        return questionService.getByTag(tag, pageNumber, pageSize)
                .map(QuestionPreviewDto::new);
    }

}
