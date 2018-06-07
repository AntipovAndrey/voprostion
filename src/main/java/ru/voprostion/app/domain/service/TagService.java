package ru.voprostion.app.domain.service;

import ru.voprostion.app.domain.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {

    List<Tag> saveAll(List<Tag> tags);

    List<Tag> saveOrGet(List<Tag> tags);

    Optional<Tag> findByName(String name);

    Tag save(Tag tag);
}
