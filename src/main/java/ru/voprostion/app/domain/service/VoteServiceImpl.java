package ru.voprostion.app.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.model.Answer;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.domain.model.Vote;
import ru.voprostion.app.repository.VoteRepository;

import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public Optional<Vote> findPreviousVote(Answer answer, User loggedIn) {
        return voteRepository.findByUserAndAnswer(loggedIn, answer);
    }

    @Override
    public Vote save(Vote vote) {
        return voteRepository.save(vote);
    }
}
