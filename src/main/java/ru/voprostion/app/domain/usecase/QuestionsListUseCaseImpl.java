package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.dto.TagDto;
import ru.voprostion.app.domain.dto.UserDto;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Tag;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.domain.service.QuestionService;
import ru.voprostion.app.domain.service.TagService;
import ru.voprostion.app.domain.service.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionsListUseCaseImpl implements QuestionsListUseCase {

    private QuestionService questionService;
    private UserService userService;
    private TagService tagService;

    @Autowired
    public QuestionsListUseCaseImpl(QuestionService questionService,
                                    UserService userService,
                                    TagService tagService) {
        this.questionService = questionService;
        this.userService = userService;
        this.tagService = tagService;
    }

    @Override
    public List<Question> getAll() {
        return questionService.getAll()
                .stream()
                .sorted(Comparator.comparing(Question::getDateCreated, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Question> getByUser(UserDto userDto) {
        final User user = userService.findByUserName(userDto.getName());
        return questionService.getByUser(user);
    }

    @Override
    public List<Question> getByTag(TagDto tagDto) {
        final Tag tag = tagService.findByName(tagDto.getName());
        return questionService.getByTag(tag);
    }
}
