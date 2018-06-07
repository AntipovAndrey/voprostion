package ru.voprostion.app.domain.service;

import ru.voprostion.app.domain.model.Answer;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.User;

import java.util.Optional;

public interface AnswerService {

    Answer save(Answer answer);

    Optional<Answer> findById(Long id);

    Optional<Answer> findPreviousAnswer(Question question, User user);

    void deleteById(Long id);
}
