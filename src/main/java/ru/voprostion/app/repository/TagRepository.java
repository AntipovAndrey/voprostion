package ru.voprostion.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.voprostion.app.domain.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
