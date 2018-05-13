package ru.voprostion.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.voprostion.app.domain.model.Answer;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.User;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Answer findByQuestionAndUser(Question question, User user);
}
