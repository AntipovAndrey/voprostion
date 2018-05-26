package ru.voprostion.app.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController implements ErrorController {

    @GetMapping("/")
    public String getAllQuestions() {
        return "redirect:/question/";
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "redirect:/404";
            }
        }
        return "error";
    }

    @GetMapping("404")
    public String notFound() {
        return "error_page";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
