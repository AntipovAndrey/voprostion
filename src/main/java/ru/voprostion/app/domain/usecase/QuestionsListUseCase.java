package ru.voprostion.app.domain.usecase;

import ru.voprostion.app.domain.dto.TagDto;
import ru.voprostion.app.domain.dto.UserDto;
import ru.voprostion.app.domain.model.Question;

import java.util.List;

public interface QuestionsListUseCase {

    List<Question> getAll();

    List<Question> getByUser(UserDto userDto);

    List<Question> getByTag(TagDto tagDto);
}
