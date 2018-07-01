package ru.voprostion.app.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.model.Tag;
import ru.voprostion.app.repository.TagRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> saveAll(List<Tag> tags) {
        return tagRepository.saveAll(tags);
    }

    @Override
    public List<Tag> saveOrGet(List<Tag> tags) {
        for (Tag tag : tags) {
            final Optional<Tag> byName = findByName(tag.getTagName());
            if (byName.isPresent()) {
                final Tag fromRepo = byName.get();
                tag.setId(fromRepo.getId());
            } else {
                save(tag);
            }
        }
        return saveAll(tags);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagRepository.findByTagNameIgnoreCase(name);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }
}
