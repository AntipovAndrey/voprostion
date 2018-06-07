package ru.voprostion.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.voprostion.app.domain.usecase.QuestionDetailsUseCase;
import ru.voprostion.app.domain.usecase.QuestionsListUseCase;

@RestController
@RequestMapping("/api/question")
public class QuestionsRestController {

    private QuestionsListUseCase questionsListUseCase;
    private QuestionDetailsUseCase questionDetailsUseCase;

    @Autowired
    public QuestionsRestController(QuestionsListUseCase questionsListUseCase,
                                   QuestionDetailsUseCase questionDetailsUseCase) {
        this.questionsListUseCase = questionsListUseCase;
        this.questionDetailsUseCase = questionDetailsUseCase;
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getAllQuestions() {
        return ResponseEntity.ok().body(questionsListUseCase.getAll());
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity<?> getDetailed(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(questionDetailsUseCase.getDetailed(id));
    }
}
