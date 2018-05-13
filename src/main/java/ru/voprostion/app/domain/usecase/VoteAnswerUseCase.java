package ru.voprostion.app.domain.usecase;

public interface VoteAnswerUseCase {

    void upVote(Long answerId);

    void downVote(Long answerId);
}
