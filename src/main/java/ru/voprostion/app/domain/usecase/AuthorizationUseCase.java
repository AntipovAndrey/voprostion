package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.model.User;

public interface AuthorizationUseCase {

    void login(User user);
}
