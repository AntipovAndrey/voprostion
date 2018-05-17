package ru.voprostion.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.voprostion.app.domain.dto.UserDto;
import ru.voprostion.app.domain.usecase.AuthorizationUseCase;
import ru.voprostion.app.domain.usecase.RegistrationUseCase;

import javax.validation.Valid;

@Controller
public class UserController {

    private RegistrationUseCase registrationUseCase;
    private AuthorizationUseCase authorizationUseCase;

    @Autowired
    public UserController(RegistrationUseCase registrationUseCase, AuthorizationUseCase authorizationUseCase) {
        this.registrationUseCase = registrationUseCase;
        this.authorizationUseCase = authorizationUseCase;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@Valid @ModelAttribute("user") UserDto userForm,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        try {
            registrationUseCase.registerNewUser(userForm);
            authorizationUseCase.login(userForm);
            // TODO: more specific exception
        } catch (Exception e) {
            model.addAttribute("usernametaken", "Username is already taken");
            model.addAttribute("user", userForm);
            return "registration";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = "/user/{name}", method = RequestMethod.GET)
    public String userPage(@PathVariable("name") String userName) {
        return "redirect:/question/user/" + userName;
    }
}
