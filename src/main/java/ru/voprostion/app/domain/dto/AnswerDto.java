package ru.voprostion.app.domain.dto;

import lombok.Data;
import ru.voprostion.app.domain.model.Answer;

import java.util.Date;

@Data
public class AnswerDto extends BaseDto {
    private Long questionId;
    private int rating;
    private int votes;
    private UserDto user;
    private String answer;
    private Integer loggedUserVote;
    private Date postDate;

    public AnswerDto(Answer answer) {
        setId(answer.getId());
        questionId = answer.getQuestion().getId();
        rating = answer.getRating();
        votes = answer.getVotes().size();
        user = new UserDto(answer.getUser());
        postDate = answer.getDateCreated();
        this.answer = answer.getAnswer();
    }
}
