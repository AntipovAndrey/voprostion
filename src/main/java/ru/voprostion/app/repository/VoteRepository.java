package ru.voprostion.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.voprostion.app.domain.model.Answer;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.domain.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    Vote findByUserAndAnswer(User owner, Answer answer);
}
