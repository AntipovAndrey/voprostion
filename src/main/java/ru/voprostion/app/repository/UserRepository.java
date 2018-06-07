package ru.voprostion.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.voprostion.app.domain.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);
}
