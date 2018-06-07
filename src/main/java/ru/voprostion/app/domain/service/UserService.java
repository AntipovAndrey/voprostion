package ru.voprostion.app.domain.service;

import ru.voprostion.app.domain.model.User;

import java.util.Optional;

public interface UserService {

    void save(User user);

    Optional<User> findByUserName(String username);

    Optional<User> getLoggedIn();
}
