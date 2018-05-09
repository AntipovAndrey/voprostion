package ru.voprostion.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.voprostion.app.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);
}
