package ru.voprostion.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.voprostion.app.domain.dto.QuestionPreviewDto;
import ru.voprostion.app.domain.usecase.QuestionDetailsUseCase;
import ru.voprostion.app.domain.usecase.QuestionsListUseCase;
import ru.voprostion.app.rest.mapper.QuestionMapper;

import java.util.Calendar;

@RestController
@RequestMapping("/api/question")
public class QuestionsRestController {

    private final QuestionsListUseCase questionsListUseCase;
    private final QuestionDetailsUseCase questionDetailsUseCase;
    private final QuestionMapper questionMapper;

    @Autowired
    public QuestionsRestController(QuestionsListUseCase questionsListUseCase,
                                   QuestionDetailsUseCase questionDetailsUseCase, QuestionMapper questionMapper) {
        this.questionsListUseCase = questionsListUseCase;
        this.questionDetailsUseCase = questionDetailsUseCase;
        this.questionMapper = questionMapper;
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getAllQuestions(@RequestParam(value = "from", required = false) Long startFrom) {
        Iterable<QuestionPreviewDto> questionPreviews =
                questionsListUseCase.getAll(startFrom == null ? Calendar.getInstance().getTime().getTime() : startFrom);
        return ResponseEntity.ok().body(questionMapper.fromIterable(questionPreviews));
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity<?> getDetailed(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(questionDetailsUseCase.getDetailed(id));
    }
}
