package ru.voprostion.app.domain.usecase;

public interface AuthorizationUseCase {

    void login(String login, String password);

    void logout();
}
