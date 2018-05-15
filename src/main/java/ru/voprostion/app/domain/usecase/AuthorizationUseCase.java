package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.dto.UserDto;

public interface AuthorizationUseCase {

    void login(UserDto userDto);
}
