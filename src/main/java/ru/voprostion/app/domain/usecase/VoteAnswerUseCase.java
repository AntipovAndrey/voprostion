package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.dto.AnswerDto;

public interface VoteAnswerUseCase {

    void upVote(Long answerId);

    void downVote(Long answerId);
}
