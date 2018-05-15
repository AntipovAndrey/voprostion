package ru.voprostion.app.domain.service;

import ru.voprostion.app.domain.model.Tag;

import java.util.List;

public interface TagService {

    List<Tag> saveAll(List<Tag> tags);

    Tag findByName(String name);

    Tag save(Tag tag);
}
