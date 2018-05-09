package ru.voprostion.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.voprostion.app.domain.model.Role;
import ru.voprostion.app.repository.RoleRepository;

@Component
public class AddDataOnStartup {

    @Autowired
    private RoleRepository roleRepository;

    @Value("${roles.authenticated}")
    private String simpleUserRole;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        roleRepository.save(new Role(simpleUserRole));
    }
}