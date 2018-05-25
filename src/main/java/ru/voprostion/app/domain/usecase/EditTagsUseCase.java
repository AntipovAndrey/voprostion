package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.model.Question;

import java.util.List;

public interface EditTagsUseCase {

    Question setNewTags(Long questionId, List<String> tags);
}
