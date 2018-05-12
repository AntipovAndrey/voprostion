package ru.voprostion.app.domain.service;

import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Tag;

import java.util.List;

public interface QuestionService {

    List<Question> getAll();

    Question create(String question, List<Tag> tags);

    Question findById(Long id);

    Question save(Question question);
}
