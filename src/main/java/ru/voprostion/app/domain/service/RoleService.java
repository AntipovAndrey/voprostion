package ru.voprostion.app.domain.service;

import ru.voprostion.app.domain.model.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findOneByName(String name);
}
