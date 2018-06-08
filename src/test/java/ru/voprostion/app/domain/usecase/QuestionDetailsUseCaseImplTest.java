package ru.voprostion.app.domain.usecase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuestionDetailsUseCaseImplTest {

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

    @Before
    public void setUp() {
        when(userService.getLoggedIn()).thenReturn(Optional.empty());
        when(voteService.findPreviousVote(any(), any())).thenReturn(Optional.empty());

        role = new Role("simple");

        user = new User() {{
            addRole(role);
            setName("Test");
            setPasswordHash("1234");
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

    @Test(expected = QuestionNotFoundException.class)
    public void throw_exception_when_requested_details_with_wrong_id() {
        // when
        when(questionService.findById(42L)).thenReturn(Optional.empty());
        // action
        useCase.getDetailed(42L);
    }

    @Test
    public void answers_contain_rating_of_logged_user() {
        // when
        when(userService.getLoggedIn()).thenReturn(Optional.of(user));
        when(voteService.findPreviousVote(answer, user)).thenReturn(Optional.of(vote));
        when(questionService.findById(42L)).thenReturn(Optional.of(question));
        // action
        final QuestionDto detailed = useCase.getDetailed(42L);
        // assert
        final AnswerDto answerDto = detailed.getAnswers().stream()
                .filter(a -> a.getAnswer().equals(answer.getAnswer()))
                .findFirst()
                .get();
        assertThat(answerDto.getLoggedUserVote(), is(equalTo(vote.getValue())));
    }
}