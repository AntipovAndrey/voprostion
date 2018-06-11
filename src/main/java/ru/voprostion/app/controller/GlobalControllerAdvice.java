package ru.voprostion.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.voprostion.app.controller.exception.NotFoundException;
import ru.voprostion.app.domain.usecase.exception.QuestionNotFoundException;
import ru.voprostion.app.domain.usecase.exception.TagNotFoundException;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Value("${app.title}")
    private String siteTitle;

    @ExceptionHandler({NotFoundException.class,
            QuestionNotFoundException.class,
            TagNotFoundException.class,
            UsernameNotFoundException.class})
    public String notFoundPage(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return "error_page";
    }

    @ModelAttribute("metaTitle")
    public void getTitle(Model model) {
        model.addAttribute("metaTitle", siteTitle);
    }
}
