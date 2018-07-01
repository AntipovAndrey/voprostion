package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.model.Answer;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.domain.model.Vote;
import ru.voprostion.app.domain.service.AnswerService;
import ru.voprostion.app.domain.service.UserService;
import ru.voprostion.app.domain.service.VoteService;
import ru.voprostion.app.domain.usecase.exception.AnswerNotFoundException;

import java.util.Objects;

@Service
public class VoteAnswerUseCaseImpl implements VoteAnswerUseCase {

    private final VoteService voteService;
    private final UserService userService;
    private final AnswerService answerService;

    @Autowired
    public VoteAnswerUseCaseImpl(VoteService voteService,
                                 UserService userService,
                                 AnswerService answerService) {
        this.voteService = voteService;
        this.userService = userService;
        this.answerService = answerService;
    }

    @Override
    public void upVote(Long answerId) {
        vote(answerId, true);
    }

    @Override
    public void downVote(Long answerId) {
        vote(answerId, false);
    }

    private void vote(Long answerId, boolean like) {
        final User loggedIn = userService.getLoggedIn()
                .orElseThrow(IllegalAccessError::new);
        final Answer answer = answerService.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException(Objects.toString(answerId)));
        if (loggedIn.equals(answer.getUser())) {
            throw new RuntimeException("you can not vote your answer");
        }
        Vote vote = voteService.findPreviousVote(answer, loggedIn).orElse(null);
        if (vote == null) {
            vote = new Vote();
            vote.setUser(loggedIn);
        }
        if (like) {
            vote.upVote();
        } else {
            vote.downVote();
        }
        answer.addVote(vote);
        voteService.save(vote);
    }
}
