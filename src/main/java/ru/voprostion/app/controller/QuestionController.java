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
import ru.voprostion.app.domain.model.Answer;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.usecase.AddAnswerUseCase;
import ru.voprostion.app.domain.usecase.AskQuestionUseCase;
import ru.voprostion.app.domain.usecase.QuestionDetailsUseCase;
import ru.voprostion.app.domain.usecase.QuestionsListUseCase;

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

    @Autowired
    public QuestionController(QuestionsListUseCase questionsListUseCase,
                              AskQuestionUseCase askQuestionUseCase,
                              QuestionDetailsUseCase questionDetailsUseCase,
                              AddAnswerUseCase addAnswerUseCase) {
        this.questionsListUseCase = questionsListUseCase;
        this.askQuestionUseCase = askQuestionUseCase;
        this.questionDetailsUseCase = questionDetailsUseCase;
        this.addAnswerUseCase = addAnswerUseCase;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getAllQuestions(Model model) {
        model.addAttribute("questions", questionsListUseCase.getAll());
        return "main";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addQuestionForm(Model model) {
        model.addAttribute("questionForm", new QuestionDto());
        return "add_question";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addQuestion(@Valid @ModelAttribute QuestionDto questionDto,
                              Model model) {
        final Question question = askQuestionUseCase.ask(questionDto);
        return "redirect:/question" + question.getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getQuestionDetails(@PathVariable("id") String id, Model model) {
        final Question question = questionDetailsUseCase.getById(Long.parseLong(id));
        final List<Answer> answers = question.getAnswers()
                .stream()
                .sorted(Comparator.comparing(Answer::getRating)
                        .thenComparing(Answer::getDateCreated, Comparator.reverseOrder())
                )
                .collect(Collectors.toList());
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        model.addAttribute("metaTitle", question.getQuestionTitle());
        model.addAttribute("answerForm", new AnswerDto());
        return "question_details";
    }

    @RequestMapping(value = "/{questionId}/like/{answerId}", method = RequestMethod.POST)
    public String likeComment(@Valid @ModelAttribute AnswerDto answerDto,
                              @PathVariable("questionId") String questionId,
                              @PathVariable("answerId") String answerId,
                              Model model) {
        // TODO: andrey : like/dislike
        return "redirect:/question/" + questionId;
    }

    @RequestMapping(value = "/{questionId}/dislike/{answerId}", method = RequestMethod.POST)
    public String dislikeComment(@Valid @ModelAttribute AnswerDto answerDto,
                                 @PathVariable("questionId") String questionId,
                                 @PathVariable("answerId") String answerId,
                                 Model model) {
        // TODO: andrey : like/dislike
        return "redirect:/question/" + questionId;
    }
}
