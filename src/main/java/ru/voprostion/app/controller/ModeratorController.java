package ru.voprostion.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.voprostion.app.controller.form.QuestionForm;
import ru.voprostion.app.domain.dto.QuestionDto;
import ru.voprostion.app.domain.dto.TagDto;
import ru.voprostion.app.domain.usecase.DeleteAnswerUseCase;
import ru.voprostion.app.domain.usecase.EditTagsUseCase;
import ru.voprostion.app.domain.usecase.QuestionDetailsUseCase;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {

    private final DeleteAnswerUseCase deleteAnswerUseCase;
    private final QuestionDetailsUseCase questionDetailsUseCase;
    private final EditTagsUseCase editTagsUseCase;

    @Autowired
    public ModeratorController(DeleteAnswerUseCase deleteAnswerUseCase,
                               QuestionDetailsUseCase questionDetailsUseCase,
                               EditTagsUseCase editTagsUseCase) {
        this.deleteAnswerUseCase = deleteAnswerUseCase;
        this.questionDetailsUseCase = questionDetailsUseCase;
        this.editTagsUseCase = editTagsUseCase;
    }

    @GetMapping("/answer/delete/{answerId:[\\d]+}")
    public String deleteAnswer(@PathVariable("answerId") Long answerId, HttpServletRequest request) {
        deleteAnswerUseCase.delete(answerId);
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/question/{questionId:[\\d]+}/edit")
    public String editQuestionForm(@PathVariable("questionId") Long questionId, Model model) {
        final QuestionForm form = new QuestionForm();
        form.setId(questionId);
        final QuestionDto questionDto = questionDetailsUseCase.getDetailed(questionId);
        form.setQuestion(questionDto.getQuestion());
        final String tagString = questionDto.getTags()
                .stream()
                .map(TagDto::getName)
                .collect(Collectors.joining(","));
        form.setTags(tagString);
        model.addAttribute("question", form);
        return "edit_tags";
    }

    @PostMapping("/question/{questionId}/edit")
    public String editQuestion(@PathVariable("questionId") Long questionId,
                               @Valid @ModelAttribute("question") QuestionForm questionForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit_tags";
        }
        editTagsUseCase.setNewTags(questionId,
                Stream.of(questionForm.getTags().split(",")).collect(Collectors.toList()));
        return "redirect:/question/" + questionId;
    }
}
