package ru.voprostion.app.domain.service;

import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Tag;
import ru.voprostion.app.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    List<Question> getAll();

    Question create(String question, List<Tag> tags);

    Optional<Question> findById(Long id);

    Question save(Question question);

    List<Question> getByUser(User user);

    List<Question> getByTag(Tag tag);
}
