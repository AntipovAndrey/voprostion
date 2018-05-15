package ru.voprostion.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.voprostion.app.domain.dto.AnswerDto;
import ru.voprostion.app.domain.dto.QuestionDto;
import ru.voprostion.app.domain.dto.TagDto;
import ru.voprostion.app.domain.dto.UserDto;
import ru.voprostion.app.domain.model.Answer;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.usecase.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/question")
public class QuestionController {

    private QuestionsListUseCase questionsListUseCase;
    private AskQuestionUseCase askQuestionUseCase;
    private QuestionDetailsUseCase questionDetailsUseCase;
    private AddAnswerUseCase addAnswerUseCase;
    private VoteAnswerUseCase voteAnswerUseCase;

    @Autowired
    public QuestionController(QuestionsListUseCase questionsListUseCase,
                              AskQuestionUseCase askQuestionUseCase,
                              QuestionDetailsUseCase questionDetailsUseCase,
                              AddAnswerUseCase addAnswerUseCase,
                              VoteAnswerUseCase voteAnswerUseCase) {
        this.questionsListUseCase = questionsListUseCase;
        this.askQuestionUseCase = askQuestionUseCase;
        this.questionDetailsUseCase = questionDetailsUseCase;
        this.addAnswerUseCase = addAnswerUseCase;
        this.voteAnswerUseCase = voteAnswerUseCase;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getAllQuestions(Model model) {
        model.addAttribute("questions", questionsListUseCase.getAll());
        return "main";
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public String getQuestionsByUser(@PathVariable("username") String username, Model model) {
        UserDto userDto = new UserDto();
        userDto.setName(username);
        model.addAttribute("questions", questionsListUseCase.getByUser(userDto));
        return "main";
    }

    @RequestMapping(value = "/tag/{tagname}", method = RequestMethod.GET)
    public String getQuestionsByTag(@PathVariable("tagname") String tagname, Model model) {
        TagDto tagDto = new TagDto();
        tagDto.setName(tagname);
        model.addAttribute("questions", questionsListUseCase.getByTag(tagDto));
        return "main";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addQuestionForm(Model model) {
        model.addAttribute("questionForm", new QuestionDto());
        return "add_question";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addQuestion(@Valid @ModelAttribute QuestionDto questionDto) {
        final Question question = askQuestionUseCase.ask(questionDto);
        return "redirect:/question/" + question.getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getQuestionDetails(@PathVariable("id") Long id, Model model) {
        final QuestionDto questionDto = new QuestionDto();
        questionDto.setId(id);
        final Question question = questionDetailsUseCase.getDetailed(questionDto);
        final List<Answer> answers = question.getAnswers()
                .stream()
                .sorted(Comparator.comparing(Answer::getRating, Comparator.reverseOrder())
                        .thenComparing(Answer::getDateCreated, Comparator.reverseOrder())
                )
                .collect(Collectors.toList());
        questionDto.setId(id);
        final boolean canAnswer = addAnswerUseCase.canAnswer(questionDto);

        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        model.addAttribute("metaTitle", question.getQuestionTitle());
        model.addAttribute("answerForm", new AnswerDto());
        model.addAttribute("canAnswer", canAnswer);
        return "question_details";
    }

    @RequestMapping(value = "/{id}/answer", method = RequestMethod.POST)
    public String answerForm(@Valid @ModelAttribute AnswerDto answerDto,
                             @PathVariable("id") Long id) {
        answerDto.setId(id);
        addAnswerUseCase.answer(answerDto);
        return "redirect:/question/" + id;
    }

    @RequestMapping(value = "/{questionId}/like/{answerId}", method = RequestMethod.GET)
    public String likeComment(@PathVariable("questionId") Long questionId,
                              @PathVariable("answerId") Long answerId) {
        final AnswerDto answerDto = new AnswerDto();
        answerDto.setId(answerId);
        voteAnswerUseCase.upVote(answerDto);
        return "redirect:/question/" + questionId;
    }

    @RequestMapping(value = "/{questionId}/dislike/{answerId}", method = RequestMethod.GET)
    public String dislikeComment(@PathVariable("questionId") Long questionId,
                                 @PathVariable("answerId") Long answerId) {
        final AnswerDto answerDto = new AnswerDto();
        answerDto.setId(answerId);
        voteAnswerUseCase.downVote(answerDto);
        return "redirect:/question/" + questionId;
    }
}
