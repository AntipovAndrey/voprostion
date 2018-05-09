package ru.voprostion.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.voprostion.app.domain.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
