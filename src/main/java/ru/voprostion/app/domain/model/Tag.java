package ru.voprostion.app.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
public class Tag extends BaseModel {
    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private Set<Question> questions = createSet();

    public void addQuestion(Question question) {
        Objects.requireNonNull(question);
        questions.add(question);
    }
}
