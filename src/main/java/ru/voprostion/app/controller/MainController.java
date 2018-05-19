package ru.voprostion.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = "/")
    public String getAllQuestions() {
        return "redirect:/question/";
    }
}
