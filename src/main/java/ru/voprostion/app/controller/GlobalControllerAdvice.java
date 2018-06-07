package ru.voprostion.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.voprostion.app.controller.exception.NotFoundException;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Value("${app.title}")
    private String siteTitle;

    @ExceptionHandler(NotFoundException.class)
    public String notFoundPage() {
        return "error_page";
    }

    @ModelAttribute("metaTitle")
    public void getTitle(Model model) {
        model.addAttribute("metaTitle", siteTitle);
    }
}
