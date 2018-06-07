package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.dto.AnswerDto;
import ru.voprostion.app.domain.dto.QuestionDto;
import ru.voprostion.app.domain.dto.TagDto;
import ru.voprostion.app.domain.model.*;
import ru.voprostion.app.domain.service.QuestionService;
import ru.voprostion.app.domain.service.UserService;
import ru.voprostion.app.domain.service.VoteService;
import ru.voprostion.app.domain.usecase.exception.QuestionNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionDetailsUseCaseImpl implements QuestionDetailsUseCase {

    private QuestionService questionService;
    private UserService userService;
    private VoteService voteService;

    @Autowired
    public QuestionDetailsUseCaseImpl(QuestionService questionService,
                                      UserService userService,
                                      VoteService voteService) {
        this.questionService = questionService;
        this.userService = userService;
        this.voteService = voteService;
    }

    @Override
    public QuestionDto getDetailed(Long questionId) {
        final Question question = questionService.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(Objects.toString(questionId)));

        final List<TagDto> tagDtos = question.getTags()
                .stream()
                .sorted(Comparator.comparing(Tag::getTagName))
                .map(TagDto::new)
                .collect(Collectors.toList());

        final Set<Answer> answerSet = question.getAnswers();
        final List<AnswerDto> answerDtos = new ArrayList<>(answerSet.size());

        final Optional<User> loggedIn = userService.getLoggedIn();

        for (Answer answer : answerSet) {
            final AnswerDto answerDto = new AnswerDto(answer);

            loggedIn.flatMap(user -> voteService.findPreviousVote(answer, user))
                    .map(Vote::getValue)
                    .ifPresent(answerDto::setLoggedUserVote);

            answerDtos.add(answerDto);
        }

        answerDtos.sort(answersByDate());

        return new QuestionDto(question, answerDtos, tagDtos);
    }

    private Comparator<AnswerDto> answersByDate() {
        return Comparator.comparing(AnswerDto::getRating, Comparator.reverseOrder())
                .thenComparing(AnswerDto::getPostDate, Comparator.reverseOrder());
    }
}
