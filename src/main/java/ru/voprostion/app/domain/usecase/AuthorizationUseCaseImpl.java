package ru.voprostion.app.domain.usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.service.SecurityService;

@Service
public class AuthorizationUseCaseImpl implements AuthorizationUseCase {

    private final SecurityService securityService;

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationUseCase.class);

    @Autowired
    public AuthorizationUseCaseImpl(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void login(String login, String password) {
        securityService.autoLogin(login, password);
    }

    @Override
    public void logout() {
        securityService.logout();
    }
}
