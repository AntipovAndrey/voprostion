package ru.voprostion.app.domain.service;

import ru.voprostion.app.domain.model.Answer;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.domain.model.Vote;

import java.util.Optional;

public interface VoteService {

    Optional<Vote> findPreviousVote(Answer answer, User loggedIn);

    Vote save(Vote vote);
}
