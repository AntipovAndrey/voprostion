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

    @Autowired
    public AddDataOnStartup(RoleRepository roleRepository,
                            UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @EventListener
    @Transactional
    public void appReady(ApplicationReadyEvent event) {
        createRoles();
        createModerator();
    }

    private void createRoles() {
        if (!roleRepository.findByName(moderatorRole).isPresent()) {
            roleRepository.save(new Role(moderatorRole));
        }
        if (!roleRepository.findByName(simpleUserRole).isPresent()) {
            roleRepository.save(new Role(simpleUserRole));
        }
    }

    private void createModerator() {
        if (userService.findByUserName(moderatorName).isPresent()) {
            return;
        }
        final Role roleModerator = roleRepository.findByName(moderatorRole).orElseThrow(IllegalStateException::new);
        final Role roleUser = roleRepository.findByName(moderatorRole).orElseThrow(IllegalStateException::new);
        User moderator = new User();
        moderator.setName(moderatorName);
        moderator.setPassword(moderatorPassword);
        moderator.addRole(roleModerator);
        moderator.addRole(roleUser);
        userService.save(moderator);
    }
}
