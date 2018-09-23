package ru.voprostion.app.domain.service;

import org.springframework.data.domain.Page;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Tag;
import ru.voprostion.app.domain.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface QuestionService {

    @Deprecated
    Page<Question> getByPage(int pageNumber, int count, String sortBy);

    Question create(String question, List<Tag> tags);

    Optional<Question> findById(Long id);

    Question save(Question question);

    @Deprecated
    Page<Question> getByUser(User user, int pageNumber, int count);

    @Deprecated
    Page<Question> getByTag(Tag tag, int pageNumber, int count);

    List<Question> getBeforeDate(Date from, int count);
}
