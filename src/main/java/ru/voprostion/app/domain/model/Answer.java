package ru.voprostion.app.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
public class Answer extends BaseModel {
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_question")
    private Question question;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.REMOVE)
    private Set<Vote> votes = createSet();

    public void setUser(User user) {
        this.user = user;
        user.addAnswer(this);
    }

    public void addVote(Vote vote) {
        Objects.requireNonNull(vote);
        if (votes.add(vote)) {
            vote.setAnswer(this);
        }
    }

    public int getRating() {
        return votes.stream()
                .mapToInt(Vote::getValue)
                .sum();
    }
}
