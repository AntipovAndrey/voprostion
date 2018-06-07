package ru.voprostion.app.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import ru.voprostion.app.domain.model.Answer;

import java.util.Date;

@Data
@JsonPropertyOrder({"id", "user", "rating", "votes", "answer", "loggedUserVote", "postDate"})
public class AnswerDto extends BaseDto {
    @JsonIgnore
    private Long questionId;
    private int rating;
    private int votes;
    private UserDto user;
    private String answer;
    @JsonInclude(JsonInclude.Include.NON_NULL)
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
