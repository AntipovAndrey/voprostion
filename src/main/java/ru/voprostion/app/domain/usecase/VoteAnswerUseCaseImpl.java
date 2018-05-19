package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.model.Answer;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.domain.model.Vote;
import ru.voprostion.app.domain.service.AnswerService;
import ru.voprostion.app.domain.service.UserService;
import ru.voprostion.app.domain.service.VoteService;

@Service
public class VoteAnswerUseCaseImpl implements VoteAnswerUseCase {

    private VoteService voteService;
    private UserService userService;
    private AnswerService answerService;

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
        final User loggedIn = userService.getLoggedIn();
        final Answer answer = answerService.findById(answerId);
        if (loggedIn == null) {
            throw new RuntimeException("not authenticated");
        }
        if (loggedIn.equals(answer.getUser())) {
            throw new RuntimeException("you can not vote your answer");
        }
        Vote vote = voteService.findPreviousVote(answer, loggedIn);
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
