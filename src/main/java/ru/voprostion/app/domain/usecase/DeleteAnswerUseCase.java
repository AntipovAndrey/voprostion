package ru.voprostion.app.domain.usecase;

import org.springframework.security.access.prepost.PreAuthorize;

public interface DeleteAnswerUseCase {

    @PreAuthorize("hasAuthority('MODERATOR')")
    void delete(Long answerId);
}
