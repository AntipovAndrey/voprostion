package ru.voprostion.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.voprostion.app.controller.form.AnswerForm;
import ru.voprostion.app.controller.form.QuestionForm;
import ru.voprostion.app.domain.dto.QuestionDto;
import ru.voprostion.app.domain.dto.QuestionPreviewDto;
import ru.voprostion.app.domain.usecase.*;

import javax.validation.Valid;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/question")
public class QuestionController {

    private final QuestionsListUseCase questionsListUseCase;
    private final AskQuestionUseCase askQuestionUseCase;
    private final QuestionDetailsUseCase questionDetailsUseCase;
    private final AddAnswerUseCase addAnswerUseCase;
    private final VoteAnswerUseCase voteAnswerUseCase;

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

    @GetMapping(value = {"/", ""})
    public String getAllQuestions(@RequestParam(value = "before", required = false) Long before, Model model) {
        long dateFrom = before == null ? Calendar.getInstance().getTime().getTime() : before;
        List<QuestionPreviewDto> questions = questionsListUseCase.getAll(dateFrom);
        model.addAttribute("questions", questions);
        if (questions != null && questions.size() != 0) {
            QuestionPreviewDto last = questions.get(questions.size() - 1);
            model.addAttribute("next", last.getDateCreated().getTime());
        }
        return "main";
    }

    @GetMapping(value = "/user/{username}")
    public String getQuestionsByUser(@PathVariable("username") String username,
                                     @RequestParam(value = "page", required = false) Integer pageNum,
                                     Model model) {
        model.addAttribute("questions", questionsListUseCase.getByUser(username, pageNum == null ? 0 : pageNum));
        return "main";
    }

    @GetMapping(value = "/tag/{tagname}")
    public String getQuestionsByTag(@PathVariable("tagname") String tagname,
                                    @RequestParam(value = "page", required = false) Integer pageNum,
                                    Model model) {
        model.addAttribute("questions", questionsListUseCase.getByTag(tagname, pageNum == null ? 0 : pageNum));
        return "main";
    }

    @GetMapping(value = "/search/tag/")
    public String searchByTag(@RequestParam(value = "page", required = false) Integer pageNum,
                              @RequestParam("tagname") String tagname) {
        final String page = pageNum == null ? "" : "&page=" + pageNum;
        return "redirect:/question/tag/" + URLEncoder.encode(tagname) + page;
    }

    @GetMapping(value = "/add")
    public String addQuestionForm(Model model) {
        model.addAttribute("questionForm", new QuestionForm());
        return "add_question";
    }

    @PostMapping(value = "/add")
    public String addQuestion(@Valid @ModelAttribute("questionForm") QuestionForm questionForm,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add_question";
        }
        final List<String> tagList = Stream.of(questionForm.getTags().split(","))
                .collect(Collectors.toList());
        final Long id = askQuestionUseCase.ask(questionForm.getQuestion(), tagList);
        return "redirect:/question/" + id;
    }

    @GetMapping("/{id:[\\d]+}")
    public String getQuestionDetails(@PathVariable("id") Long questionId, Model model) {
        final QuestionDto question = questionDetailsUseCase.getDetailed(questionId);

        if (addAnswerUseCase.canAnswer(questionId)) {
            model.addAttribute("answerForm", new AnswerForm());
        }

        model.addAttribute("question", question);
        model.addAttribute("metaTitle", question.getQuestion());

        return "question_details";
    }

    @PostMapping("/{id:[\\d]+}/answer")
    public String answerForm(@Valid @ModelAttribute AnswerForm answerForm,
                             @PathVariable("id") Long id) {
        addAnswerUseCase.answer(id, answerForm.getAnswer());
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
