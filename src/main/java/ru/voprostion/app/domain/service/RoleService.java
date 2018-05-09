package ru.voprostion.app.domain.service;

import ru.voprostion.app.domain.model.Role;

public interface RoleService {

    Role findOneByName(String name);
}
