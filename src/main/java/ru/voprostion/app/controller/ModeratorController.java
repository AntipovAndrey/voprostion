package ru.voprostion.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.voprostion.app.domain.dto.QuestionDto;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Tag;
import ru.voprostion.app.domain.usecase.DeleteAnswerUseCase;
import ru.voprostion.app.domain.usecase.EditTagsUseCase;
import ru.voprostion.app.domain.usecase.QuestionDetailsUseCase;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class ModeratorController {

    private DeleteAnswerUseCase deleteAnswerUseCase;
    private QuestionDetailsUseCase questionDetailsUseCase;
    private EditTagsUseCase editTagsUseCase;

    @Autowired
    public ModeratorController(DeleteAnswerUseCase deleteAnswerUseCase,
                               QuestionDetailsUseCase questionDetailsUseCase,
                               EditTagsUseCase editTagsUseCase) {
        this.deleteAnswerUseCase = deleteAnswerUseCase;
        this.questionDetailsUseCase = questionDetailsUseCase;
        this.editTagsUseCase = editTagsUseCase;
    }

    @GetMapping("/moderator/answer/delete/{answerId:[\\d]+}")
    public String deleteAnswer(@PathVariable("answerId") Long answerId, HttpServletRequest request) {
        deleteAnswerUseCase.delete(answerId);
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/question/{questionId:[\\d]+}/edit")
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
        model.addAttribute("question", dto);
        return "edit_tags";
    }

    @PostMapping("/question/{questionId}/edit")
    public String editQuestion(@PathVariable("questionId") Long questionId,
                               @Valid @ModelAttribute("question") QuestionDto questionDto) {
        editTagsUseCase.setNewTags(questionId,
                Stream.of(questionDto.getTags().split(",")).collect(Collectors.toList()));
        return "redirect:/question/" + questionId;
    }
}
