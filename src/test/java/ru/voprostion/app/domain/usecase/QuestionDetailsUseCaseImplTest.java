package ru.voprostion.app.domain.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.voprostion.app.domain.dto.AnswerDto;
import ru.voprostion.app.domain.dto.QuestionDto;
import ru.voprostion.app.domain.model.*;
import ru.voprostion.app.domain.service.QuestionService;
import ru.voprostion.app.domain.service.UserService;
import ru.voprostion.app.domain.service.VoteService;
import ru.voprostion.app.domain.usecase.exception.QuestionNotFoundException;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class QuestionDetailsUseCaseImplTest {

    @Mock
    private QuestionService questionService;
    @Mock
    private UserService userService;
    @Mock
    private VoteService voteService;

    @InjectMocks
    private QuestionDetailsUseCaseImpl useCase;

    private User user;
    private Question question;
    private Answer answer;
    private Role role;
    private Vote vote;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(userService.getLoggedIn()).thenReturn(Optional.empty());
        when(voteService.findPreviousVote(any(), any())).thenReturn(Optional.empty());

        role = new Role("simple");

        user = new User() {{
            addRole(role);
            setName("Test");
            setPassword("1234");
            setId(1337L);
        }};

        vote = new Vote() {{
            upVote();
        }};

        answer = new Answer() {{
            setUser(new User() {{
                setId(10L);
                setName("user2");
            }});
            setAnswer("answer text");
            addVote(vote);
        }};

        question = new Question() {{
            setId(42L);
            setUser(new User() {{
                setId(11L);
                setName("user3");
            }});
            setQuestionTitle("question text");
            addAnswer(answer);
        }};
    }

    @Test
    void whenDetailedQuestionRequestedWithWrongIdThenThrowException() {
        when(questionService.findById(42L)).thenReturn(Optional.empty());
        assertThrows(QuestionNotFoundException.class, () -> useCase.getDetailed(42L));
    }

    @Test
    void whenDetailedQuestionRequestedThenModelMustContainLoggedUserVote() {
        when(userService.getLoggedIn()).thenReturn(Optional.of(user));
        when(voteService.findPreviousVote(answer, user)).thenReturn(Optional.of(vote));
        when(questionService.findById(42L)).thenReturn(Optional.of(question));

        final QuestionDto detailed = useCase.getDetailed(42L);

        final AnswerDto answerDto = detailed.getAnswers().stream()
                .filter(a -> a.getAnswer().equals(answer.getAnswer()))
                .findFirst().get();
        assertThat(answerDto.getLoggedUserVote(), is(equalTo(vote.getValue())));
    }
}