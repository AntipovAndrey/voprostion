package ru.voprostion.app.domain.service;

import org.springframework.data.domain.Page;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Tag;
import ru.voprostion.app.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    Page<Question> getByPage(int pageNumber, String sortBy);

    Question create(String question, List<Tag> tags);

    Optional<Question> findById(Long id);

    Question save(Question question);

    Page<Question> getByUser(User user, int pageNumber, String sortBy);

    Page<Question> getByTag(Tag tag, int pageNumber, String sortBy);
}
