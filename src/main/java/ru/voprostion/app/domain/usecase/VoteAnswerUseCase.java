package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.dto.AnswerDto;

public interface VoteAnswerUseCase {

    void upVote(AnswerDto answerDto);

    void downVote(AnswerDto answerDto);
}
