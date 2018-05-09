package ru.voprostion.app.domain.service;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}