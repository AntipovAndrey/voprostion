package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.dto.QuestionDto;
import ru.voprostion.app.domain.dto.TagDto;
import ru.voprostion.app.domain.model.Question;

import java.util.List;

public interface EditTagsUseCase {

    Question setNewTags(Long questionId, List<String> tags);
}
