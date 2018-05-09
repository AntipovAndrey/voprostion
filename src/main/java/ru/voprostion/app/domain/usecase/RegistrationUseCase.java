package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.model.User;

import javax.validation.Valid;

public interface RegistrationUseCase {

    void registerNewUser(@Valid User user);
}
