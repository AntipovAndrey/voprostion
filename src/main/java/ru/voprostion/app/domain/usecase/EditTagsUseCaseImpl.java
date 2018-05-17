package ru.voprostion.app.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.voprostion.app.domain.dto.QuestionDto;
import ru.voprostion.app.domain.dto.TagDto;
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
    public Question setNewTags(QuestionDto questionDto, List<TagDto> tagDtos) {
        if (tagDtos == null || tagDtos.size() < 3) {
            throw new IllegalArgumentException();
        }
        List<Tag> tagList = tagDtos.stream()
                .map(TagDto::getName)
                .map(Tag::new)
                .collect(Collectors.toList());
        tagList = tagService.saveOrGet(tagList);
        final Question question = questionService.findById(questionDto.getId());
        question.setTags(new HashSet<>(tagList));
        return questionService.save(question);
    }
}
