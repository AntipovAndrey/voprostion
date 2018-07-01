package ru.voprostion.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Tag;
import ru.voprostion.app.domain.model.User;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findAllByUser(User user, Pageable pageable);

    Page<Question> findAllByTags(Tag tag, Pageable pageable);
}
