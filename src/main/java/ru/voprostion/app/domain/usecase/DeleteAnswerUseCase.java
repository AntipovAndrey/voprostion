package ru.voprostion.app.domain.usecase;

import org.springframework.security.access.prepost.PreAuthorize;
import ru.voprostion.app.domain.dto.AnswerDto;

public interface DeleteAnswerUseCase {

    @PreAuthorize("hasAuthority('MODERATOR')")
    void delete(Long answerId);
}
