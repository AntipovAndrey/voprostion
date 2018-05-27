package ru.voprostion.app.domain.usecase;

import java.util.List;

public interface EditTagsUseCase {

    void setNewTags(Long questionId, List<String> tags);
}
