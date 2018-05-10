package ru.voprostion.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.voprostion.app.domain.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
