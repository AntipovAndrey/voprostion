package ru.voprostion.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.voprostion.app.domain.model.Role;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.domain.service.UserService;
import ru.voprostion.app.repository.RoleRepository;
import ru.voprostion.app.repository.UserRepository;

@Component
public class AddDataOnStartup {

    private final RoleRepository roleRepository;
    private final UserService userService;

    @Value("${roles.authenticated}")
    private String simpleUserRole;

    @Value("${roles.authenticated.moderator}")
    private String moderatorRole;

    @Value("${users.moderator.name}")
    private String moderatorName;

    @Value("${users.moderator.password}")
    private String moderatorPassword;

    @Value("${app.filldb}")
    private Boolean databaseUpdate;

    @Autowired
    public AddDataOnStartup(RoleRepository roleRepository,
                            UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @EventListener
    @Transactional
    public void appReady(ApplicationReadyEvent event) {
        if (!databaseUpdate) return;
        final Role moderatorRole = roleRepository.save(new Role(this.moderatorRole));
        final Role userRole = roleRepository.save(new Role(simpleUserRole));
        User moderator = new User();
        moderator.setName(moderatorName);
        moderator.setPassword(moderatorPassword);
        moderator.setPasswordHash(moderatorPassword);
        moderator.addRole(moderatorRole);
        moderator.addRole(userRole);
        userService.save(moderator);
    }
}
