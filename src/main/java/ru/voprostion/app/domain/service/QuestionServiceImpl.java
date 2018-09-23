package ru.voprostion.app.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Tag;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.repository.QuestionRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Order;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final TagService tagService;
    private final UserService userService;
    private final Sort sort;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository,
                               TagService tagService,
                               UserService userService,
                               @Value("${question.sortingKey}") String sortingKey) {
        this.questionRepository = questionRepository;
        this.tagService = tagService;
        this.userService = userService;
        this.sort = Sort.by(Order.desc(sortingKey));
    }

    @Override
    public Page<Question> getByPage(int pageNumber, int count, String sortBy) {
        return questionRepository.findAll(PageRequest.of(pageNumber, count, Sort.by(Order.desc(sortBy))));
    }

    @Override
    public Question create(String questionString, List<Tag> tags) {
        Question question = new Question();
        question.setQuestionTitle(questionString);
        userService.getLoggedIn().ifPresent(question::setUser);
        tags = tagService.saveOrGet(tags);
        tags.forEach(question::addTag);
        return questionRepository.save(question);
    }

    @Override
    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Page<Question> getByUser(User user, int pageNumber, int count) {
        return questionRepository.findAllByUser(user, PageRequest.of(pageNumber, count, sort));
    }

    @Override
    public Page<Question> getByTag(Tag tag, int pageNumber, int count) {
        return questionRepository.findAllByTags(tag, PageRequest.of(pageNumber, count, sort));
    }

    @Override
    public List<Question> getBeforeDate(Date from, int count) {
        return questionRepository.findAllByDateCreatedBefore(from,
                PageRequest.of(0, count, sort));
    }
}
