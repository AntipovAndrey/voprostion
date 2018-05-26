package ru.voprostion.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.voprostion.app.controller.exception.NotFoundException;
import ru.voprostion.app.domain.dto.AnswerDto;
import ru.voprostion.app.domain.dto.QuestionDto;
import ru.voprostion.app.domain.model.Answer;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.usecase.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/question")
public class QuestionController {

    private QuestionsListUseCase questionsListUseCase;
    private AskQuestionUseCase askQuestionUseCase;
    private QuestionDetailsUseCase questionDetailsUseCase;
    private AddAnswerUseCase addAnswerUseCase;
    private VoteAnswerUseCase voteAnswerUseCase;
    private AnswersListUseCase answersListUseCase;

    @Autowired
    public QuestionController(QuestionsListUseCase questionsListUseCase,
                              AskQuestionUseCase askQuestionUseCase,
                              QuestionDetailsUseCase questionDetailsUseCase,
                              AddAnswerUseCase addAnswerUseCase,
                              VoteAnswerUseCase voteAnswerUseCase,
                              AnswersListUseCase answersListUseCase) {
        this.questionsListUseCase = questionsListUseCase;
        this.askQuestionUseCase = askQuestionUseCase;
        this.questionDetailsUseCase = questionDetailsUseCase;
        this.addAnswerUseCase = addAnswerUseCase;
        this.voteAnswerUseCase = voteAnswerUseCase;
        this.answersListUseCase = answersListUseCase;
    }

    @GetMapping(value = "/")
    public String getAllQuestions(Model model) {
        model.addAttribute("questions", questionsListUseCase.getAll());
        return "main";
    }

    @GetMapping(value = "/user/{username}")
    public String getQuestionsByUser(@PathVariable("username") String username, Model model) {
        model.addAttribute("questions", questionsListUseCase.getByUser(username));
        return "main";
    }

    @GetMapping(value = "/tag/{tagname}")
    public String getQuestionsByTag(@PathVariable("tagname") String tagname, Model model) {
        model.addAttribute("questions", questionsListUseCase.getByTag(tagname));
        return "main";
    }

    @GetMapping(value = "/search/tag/")
    public String searchByTag(@RequestParam("tagname") String tagname) {
        return "redirect:/question/tag/" + tagname;
    }

    @GetMapping(value = "/add")
    public String addQuestionForm(Model model) {
        model.addAttribute("questionForm", new QuestionDto());
        return "add_question";
    }

    @PostMapping(value = "/add")
    public String addQuestion(@Valid @ModelAttribute("questionForm") QuestionDto questionDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add_question";
        }
        final List<String> tagList = Stream.of(questionDto.getTags().split(","))
                .collect(Collectors.toList());
        final Question question = askQuestionUseCase.ask(questionDto.getQuestion(), tagList);
        return "redirect:/question/" + question.getId();
    }

    @GetMapping("/{id:[\\d]+}")
    public String getQuestionDetails(@PathVariable("id") Long questionId, Model model) {
        final Question question = questionDetailsUseCase.getDetailed(questionId);

        if (question == null) throw new NotFoundException();

        final boolean canAnswer = addAnswerUseCase.canAnswer(questionId);
        final List<Answer> answers = answersListUseCase.getAll(questionId);

        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        model.addAttribute("metaTitle", question.getQuestionTitle());
        model.addAttribute("answerForm", new AnswerDto());
        model.addAttribute("canAnswer", canAnswer);

        return "question_details";
    }

    @PostMapping("/{id:[\\d]+}/answer")
    public String answerForm(@Valid @ModelAttribute AnswerDto answerDto,
                             @PathVariable("id") Long id) {
        addAnswerUseCase.answer(id, answerDto.getAnswer());
        return "redirect:/question/" + id;
    }

    @GetMapping("/{questionId:[\\d]+}/like/{answerId:[\\d]+}")
    public String likeComment(@PathVariable("questionId") Long questionId,
                              @PathVariable("answerId") Long answerId) {
        voteAnswerUseCase.upVote(answerId);
        return "redirect:/question/" + questionId;
    }

    @GetMapping("/{questionId:[\\d]+}/dislike/{answerId:[\\d]+}")
    public String dislikeComment(@PathVariable("questionId") Long questionId,
                                 @PathVariable("answerId") Long answerId) {
        voteAnswerUseCase.downVote(answerId);
        return "redirect:/question/" + questionId;
    }
}
