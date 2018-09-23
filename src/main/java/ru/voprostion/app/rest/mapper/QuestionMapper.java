package ru.voprostion.app.rest.mapper;

import ru.voprostion.app.domain.dto.QuestionPreviewDto;
import ru.voprostion.app.rest.model.QuestionPreviewList;

public interface QuestionMapper {

    QuestionPreviewList fromIterable(Iterable<QuestionPreviewDto> questionPreviewDtos);
}
