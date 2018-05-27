package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.model.Question;
import ru.voprostion.app.domain.model.Tag;
import ru.voprostion.app.domain.service.QuestionService;
import ru.voprostion.app.domain.service.TagService;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EditTagsUseCaseImpl implements EditTagsUseCase {

    private QuestionService questionService;
    private TagService tagService;

    @Autowired
    public EditTagsUseCaseImpl(QuestionService questionService, TagService tagService) {
        this.questionService = questionService;
        this.tagService = tagService;
    }

    @Override
    public void setNewTags(Long questionId, List<String> tags) {
        if (tags == null || tags.size() < 1) {
            throw new IllegalArgumentException();
        }
        List<Tag> tagList = tags.stream()
                .map(Tag::new)
                .collect(Collectors.toList());
        tagList = tagService.saveOrGet(tagList);
        final Question question = questionService.findById(questionId);
        question.setTags(new HashSet<>(tagList));
        questionService.save(question);
    }
}
