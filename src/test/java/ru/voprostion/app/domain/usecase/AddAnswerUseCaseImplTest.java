package ru.voprostion.app.domain.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.voprostion.app.domain.model.Answer;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Role;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.domain.service.AnswerService;
import ru.voprostion.app.domain.service.QuestionService;
import ru.voprostion.app.domain.service.UserService;
import ru.voprostion.app.domain.usecase.exception.QuestionNotFoundException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddAnswerUseCaseImplTest {

    @Mock
    private AnswerService answerService;

    @Mock
    private UserService userService;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private AddAnswerUseCaseImpl addAnswerUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void whenNoUserLoggedInThenCantAnswer() {
        when(userService.getLoggedIn()).thenReturn(Optional.empty());

        assertThat(addAnswerUseCase.canAnswer(1L), is(equalTo(false)));
    }

    @Nested
    class UserLoggedIn {

        private User user;
        private User secondUser;
        private Question question;
        private String answerText;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.initMocks(this);
            user = new User() {{
                setId(1L);
                addRole(new Role("simple"));
                setName("Test");
                setPassword("1234");
            }};

            secondUser = new User() {{
                setId(2L);
                addRole(new Role("simple"));
                setName("Test2");
                setPassword("1234");
            }};

            question = new Question() {{
                setId(1L);
                setUser(secondUser);
                setQuestionTitle("question text");
            }};

            answerText = "answer";

            when(questionService.findById(question.getId())).thenReturn(Optional.of(question));
        }

        @Test
        void whenLoggedUserIsTheOwnerThenCantAnswer() {
            when(userService.getLoggedIn()).thenReturn(Optional.of(secondUser));

            assertThat(addAnswerUseCase.canAnswer(question.getId()), is(equalTo(false)));
        }

        @Test
        void whenLoggedUserIsNotTheOwnerAndHasPreviousAnswersThenCantAnswer() {
            when(userService.getLoggedIn()).thenReturn(Optional.of(user));
            when(answerService.findPreviousAnswer(question, user)).thenReturn(Optional.of(new Answer()));

            assertThat(addAnswerUseCase.canAnswer(user.getId()), is(equalTo(false)));
        }

        @Test
        void whenLoggedUserIsNotTheOwnerAndHasNoPreviousAnswersThenCanAnswer() {
            when(userService.getLoggedIn()).thenReturn(Optional.of(user));
            when(answerService.findPreviousAnswer(question, user)).thenReturn(Optional.empty());

            assertThat(addAnswerUseCase.canAnswer(question.getId()), is(equalTo(true)));
        }

        @Test
        void whenUserLoggedInAndQuestionFoundThenSaveAnswer() {
            when(userService.getLoggedIn()).thenReturn(Optional.of(user));
            when(questionService.findById(question.getId())).thenReturn(Optional.of(question));

            addAnswerUseCase.answer(question.getId(), answerText);

            ArgumentCaptor<Answer> argumentCaptor = ArgumentCaptor.forClass(Answer.class);
            verify(answerService).save(argumentCaptor.capture());
            assertThat(argumentCaptor.getValue().getAnswer(), equalTo(answerText));
            assertThat(argumentCaptor.getValue().getUser(), equalTo(user));
            assertThat(argumentCaptor.getValue().getQuestion(), equalTo(question));
        }

        @Test
        void whenQuestionNotFoundThenThrowException() {
            when(questionService.findById(question.getId())).thenReturn(Optional.empty());
            assertThrows(QuestionNotFoundException.class, () ->
                    addAnswerUseCase.answer(question.getId(), answerText)
            );
        }
    }
}