package ru.voprostion.app.rest.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.voprostion.app.domain.dto.QuestionPreviewDto;
import ru.voprostion.app.rest.model.QuestionPreviewList;
import ru.voprostion.app.rest.model.QuestionPreviewRest;

import java.util.Iterator;

@Component
public class QuestionMapperImpl implements QuestionMapper {
    private final String baseUrl;

    public QuestionMapperImpl(@Value("${app.baseUrl}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public QuestionPreviewList fromIterable(Iterable<QuestionPreviewDto> questionPreviewDtos) {
        QuestionPreviewList questionPreviewList = new QuestionPreviewList(questionPreviewDtos);

        final Iterator<QuestionPreviewDto> itr = questionPreviewDtos.iterator();

        if (itr.hasNext()) {
            QuestionPreviewDto lastElement = itr.next();
            while (itr.hasNext()) {
                lastElement = itr.next();
            }
            questionPreviewList.setNext(baseUrl + "/api/question?from=" + lastElement.getDateCreated().getTime());
        } else {
            questionPreviewList.setNext(null);
        }

        for (QuestionPreviewRest question : questionPreviewList.getQuestions()) {
            question.setDetails(baseUrl + "/api/question/" + question.getQuestion().getId());
        }

        return questionPreviewList;
    }
}
