package ru.voprostion.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.voprostion.app.domain.dto.QuestionDto;
import ru.voprostion.app.domain.usecase.AskQuestionUseCase;
import ru.voprostion.app.domain.usecase.QuestionsListUseCase;

import javax.validation.Valid;

@Controller
@RequestMapping("/question")
public class QuestionController {

    private QuestionsListUseCase questionsListUseCase;
    private AskQuestionUseCase askQuestionUseCase;

    @Autowired
    public QuestionController(QuestionsListUseCase questionsListUseCase,
                              AskQuestionUseCase askQuestionUseCase) {
        this.questionsListUseCase = questionsListUseCase;
        this.askQuestionUseCase = askQuestionUseCase;
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
        askQuestionUseCase.ask(questionDto);
        // TODO: 12.05.18 andrey: redirect to the question
        return "redirect:/";
    }
}
