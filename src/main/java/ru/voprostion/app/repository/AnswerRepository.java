package ru.voprostion.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.voprostion.app.domain.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
