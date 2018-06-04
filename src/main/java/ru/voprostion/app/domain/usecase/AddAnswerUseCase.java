package ru.voprostion.app.domain.usecase;

public interface AddAnswerUseCase {

    Long answer(Long questionId, String answer);

    boolean canAnswer(Long questionId);
}
