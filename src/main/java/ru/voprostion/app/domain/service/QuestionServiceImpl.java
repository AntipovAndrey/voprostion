package ru.voprostion.app.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Tag;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.repository.QuestionRepository;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepository questionRepository;
    private TagService tagService;
    private UserService userService;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository,
                               TagService tagService,
                               UserService userService) {
        this.questionRepository = questionRepository;
        this.tagService = tagService;
        this.userService = userService;
    }

    @Override
    public List<Question> getAll() {
        return questionRepository.findAll();
    }

    @Override
    public Question create(String questionString, List<Tag> tags) {
        Question question = new Question();
        question.setQuestionTitle(questionString);
        question.setUser(userService.getLoggedIn());
        tags = tagService.saveOrGet(tags);
        tags.forEach(question::addTag);
        return questionRepository.save(question);
    }

    @Override
    public Question findById(Long id) {
        return questionRepository.findOne(id);
    }

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getByUser(User user) {
        return questionRepository.findAllByUser(user);
    }

    @Override
    public List<Question> getByTag(Tag tag) {
        return questionRepository.findAllByTags(tag);
    }
}
