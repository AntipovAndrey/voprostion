package ru.voprostion.app.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.model.Tag;
import ru.voprostion.app.repository.TagRepository;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> saveAll(List<Tag> tags) {
        return tagRepository.save(tags);
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByTagNameIgnoreCase(name);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }
}
