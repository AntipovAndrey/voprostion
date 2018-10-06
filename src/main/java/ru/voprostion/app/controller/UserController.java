package ru.voprostion.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.voprostion.app.controller.form.UserForm;
import ru.voprostion.app.domain.usecase.AuthorizationUseCase;
import ru.voprostion.app.domain.usecase.RegistrationUseCase;
import ru.voprostion.app.domain.usecase.exception.UserAlreadyRegistered;

import javax.validation.Valid;

@Controller
public class UserController {

    private final RegistrationUseCase registrationUseCase;
    private final AuthorizationUseCase authorizationUseCase;

    @Autowired
    public UserController(RegistrationUseCase registrationUseCase, AuthorizationUseCase authorizationUseCase) {
        this.registrationUseCase = registrationUseCase;
        this.authorizationUseCase = authorizationUseCase;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new UserForm());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") UserForm userForm,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        try {
            registrationUseCase.registerNewUser(userForm.getName(), userForm.getPassword());
            authorizationUseCase.login(userForm.getName(), userForm.getPassword());
        } catch (UserAlreadyRegistered e) {
            model.addAttribute("usernametaken", "Username is already taken");
            model.addAttribute("user", userForm);
            return "registration";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Your username or password is invalid.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }

        return "login";
    }

    @GetMapping("/user/{name}")
    public String userPage(@PathVariable("name") String userName) {
        return "redirect:/question/user/" + userName;
    }
}
