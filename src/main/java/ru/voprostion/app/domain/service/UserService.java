package ru.voprostion.app.domain.service;

import ru.voprostion.app.domain.model.User;

public interface UserService {

    void save(User user);

    User findByUserName(String username);

    User getLoggedIn();
}
