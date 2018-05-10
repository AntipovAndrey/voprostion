package ru.voprostion.app.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
public class Question extends BaseModel {
    private String questionTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user")
    private User user;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private Set<Answer> answers = createSet();

    @ManyToMany
    @JoinTable(name = "question_tag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = createSet();

    public void setUser(User user) {
        this.user = user;
        user.addQuestion(this);
    }

    public void addAnswer(Answer answer) {
        Objects.requireNonNull(answer);
        if (answers.add(answer)) {
            answer.setQuestion(this);
        }
    }

    public void addTag(Tag tag) {
        Objects.requireNonNull(tag);
        if (tags.add(tag)) {
            tag.addQuestion(this);
        }
    }

    public void removeAnswer(Answer answer) {
        Objects.requireNonNull(answer);
        if (answers.contains(answer)) {
            answers.remove(answer);
        }
    }

    public void removeTag(Tag tag) {
        Objects.requireNonNull(tag);
        if (tags.contains(tag)) {
            tags.remove(tag);
            tag.removeQuestion(this);
        }
    }

}
