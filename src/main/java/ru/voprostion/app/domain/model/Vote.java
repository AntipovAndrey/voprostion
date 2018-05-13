package ru.voprostion.app.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Vote extends BaseModel {
    private int value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_answer")
    private Answer answer;

    public void setUser(User user) {
        this.user = user;
        user.addVote(this);
    }

    public void upVote() {
        value = 1;
    }

    public void downVote() {
        value = -1;
    }
}
