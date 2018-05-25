package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.model.Answer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswersListUseCaseImpl implements AnswersListUseCase {

    private QuestionDetailsUseCase questionDetailsUseCase;

    @Autowired
    public AnswersListUseCaseImpl(QuestionDetailsUseCase questionDetailsUseCase) {
        this.questionDetailsUseCase = questionDetailsUseCase;
    }

    @Override
    public List<Answer> getAll(Long questionId) {
        return questionDetailsUseCase.getDetailed(questionId).getAnswers()
                .stream()
                .sorted(Comparator.comparing(Answer::getRating, Comparator.reverseOrder())
                        .thenComparing(Answer::getDateCreated, Comparator.reverseOrder())
                )
                .collect(Collectors.toList());
    }
}
