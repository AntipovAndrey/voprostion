package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.dto.UserDto;

import javax.validation.Valid;

public interface RegistrationUseCase {

    void registerNewUser(String name, String password);
}
