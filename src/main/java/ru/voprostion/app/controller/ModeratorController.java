package ru.voprostion.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.voprostion.app.domain.usecase.DeleteAnswerUseCase;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {

    private DeleteAnswerUseCase deleteAnswerUseCase;

    @Autowired
    public ModeratorController(DeleteAnswerUseCase deleteAnswerUseCase) {
        this.deleteAnswerUseCase = deleteAnswerUseCase;
    }

    @GetMapping(value = "/answer/delete/{answerId}")
    public String deleteAnswer(@PathVariable("answerId") Long answerId) {
        deleteAnswerUseCase.delete(answerId);
        return "redirect:/";
    }
}