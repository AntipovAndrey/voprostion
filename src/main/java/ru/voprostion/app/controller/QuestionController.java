package ru.voprostion.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.voprostion.app.domain.dto.AnswerDto;
import ru.voprostion.app.domain.dto.QuestionDto;
import ru.voprostion.app.domain.model.Answer;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Tag;
import ru.voprostion.app.domain.usecase.*;

import javax.validation.Valid;
import java.util.Comparator;
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
        final Question question = askQuestionUseCase.ask(questionDto.getQuestion(),
                Stream.of(questionDto.getTags().split(","))
                        .collect(Collectors.toList())
        );
        return "redirect:/question/" + question.getId();
    }

    @GetMapping(value = "/{id}")
    public String getQuestionDetails(@PathVariable("id") Long id, Model model) {
        final Question question = questionDetailsUseCase.getDetailed(id);
        final List<Answer> answers = question.getAnswers()
                .stream()
                .sorted(Comparator.comparing(Answer::getRating, Comparator.reverseOrder())
                        .thenComparing(Answer::getDateCreated, Comparator.reverseOrder())
                )
                .collect(Collectors.toList());
        final boolean canAnswer = addAnswerUseCase.canAnswer(id);

        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        model.addAttribute("metaTitle", question.getQuestionTitle());
        model.addAttribute("answerForm", new AnswerDto());
        model.addAttribute("canAnswer", canAnswer);
        return "question_details";
    }

    @PostMapping(value = "/{id}/answer")
    public String answerForm(@Valid @ModelAttribute AnswerDto answerDto,
                             @PathVariable("id") Long id) {
        addAnswerUseCase.answer(id, answerDto.getAnswer());
        return "redirect:/question/" + id;
    }

    @GetMapping(value = "/{questionId}/like/{answerId}")
    public String likeComment(@PathVariable("questionId") Long questionId,
                              @PathVariable("answerId") Long answerId) {
        voteAnswerUseCase.upVote(answerId);
        return "redirect:/question/" + questionId;
    }

    @GetMapping(value = "/{questionId}/dislike/{answerId}")
    public String dislikeComment(@PathVariable("questionId") Long questionId,
                                 @PathVariable("answerId") Long answerId) {
        voteAnswerUseCase.downVote(answerId);
        return "redirect:/question/" + questionId;
    }

    @GetMapping(value = "/{questionId}/edit")
    public String editQuestionForm(@PathVariable("questionId") Long questionId, Model model) {
        final QuestionDto dto = new QuestionDto();
        dto.setId(questionId);
        final Question question = questionDetailsUseCase.getDetailed(questionId);
        dto.setQuestion(question.getQuestionTitle());
        final String tagString = question.getTags()
                .stream()
                .map(Tag::getTagName)
                .collect(Collectors.joining(","));
        dto.setTags(tagString);
        model.addAttribute("questionForm", dto);
        return "add_question";
    }
}
