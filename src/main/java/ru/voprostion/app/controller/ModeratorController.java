package ru.voprostion.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.voprostion.app.domain.dto.AnswerDto;
import ru.voprostion.app.domain.usecase.DeleteAnswerUseCase;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {

    private DeleteAnswerUseCase deleteAnswerUseCase;

    @Autowired
    public ModeratorController(DeleteAnswerUseCase deleteAnswerUseCase) {
        this.deleteAnswerUseCase = deleteAnswerUseCase;
    }

    @RequestMapping(value = "/answer/delete/{answerId}", method = RequestMethod.GET)
    public String getAllQuestions(@PathVariable("answerId") Long answerId, Model model) {
        final AnswerDto answerDto = new AnswerDto();
        answerDto.setId(answerId);
        deleteAnswerUseCase.delete(answerDto);
        return "redirect:/";
    }
}
