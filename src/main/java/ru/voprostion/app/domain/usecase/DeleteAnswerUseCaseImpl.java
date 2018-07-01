package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.service.AnswerService;

@Service
public class DeleteAnswerUseCaseImpl implements DeleteAnswerUseCase {

    private final AnswerService answerService;

    @Autowired
    public DeleteAnswerUseCaseImpl(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Override
    public void delete(Long answerId) {
        answerService.deleteById(answerId);
    }
}
