package ru.voprostion.app.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.model.User;
import ru.voprostion.app.repository.RoleRepository;
import ru.voprostion.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private SecurityService securityService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           SecurityService securityService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.securityService = securityService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void save(User user) {
        user.setPasswordHash(bCryptPasswordEncoder.encode(user.getPasswordHash()));
        userRepository.save(user);
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByName(username);
    }

    @Override
    public User getLoggedIn() {
        return userRepository.findByName(securityService.findLoggedInUsername());
    }
}
