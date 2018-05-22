package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.model.Role;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.domain.service.RoleService;
import ru.voprostion.app.domain.service.UserService;

@Service
public class RegistrationUseCaseImpl implements RegistrationUseCase {

    private UserService userService;
    private RoleService roleService;
    private String simpleUserRole;

    public RegistrationUseCaseImpl(UserService userService,
                                   RoleService roleService,
                                   @Value("${roles.authenticated}") String simpleUserRole) {
        this.userService = userService;
        this.roleService = roleService;
        this.simpleUserRole = simpleUserRole;
    }

    @Override
    public void registerNewUser(String name, String password) {
        final Role authRole = roleService.findOneByName(simpleUserRole);
        final User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.addRole(authRole);
        userService.save(user);
    }
}
