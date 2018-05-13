package ru.voprostion.app.domain.service;

import ru.voprostion.app.domain.model.Answer;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.User;

public interface AnswerService {

    Answer save(Answer answer);

    Answer findById(Long id);

    Answer findPreviousAnswer(Question question, User user);
}
