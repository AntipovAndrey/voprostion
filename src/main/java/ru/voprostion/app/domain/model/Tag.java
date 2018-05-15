package ru.voprostion.app.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tag extends BaseModel {
    @NotNull
    @Column(unique = true)
    private String tagName;

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    @ManyToMany(mappedBy = "tags")
    private Set<Question> questions = createSet();

    public void addQuestion(Question question) {
        Objects.requireNonNull(question);
        questions.add(question);
    }

    public void removeQuestion(Question question) {
        Objects.requireNonNull(question);
        questions.remove(question);
    }

    public static List<Tag> fromString(String tags, String delim) {
        return Stream.of(tags.split(delim))
                .map(Tag::new)
                .collect(Collectors.toList());
    }

    public static List<Tag> fromString(String tags) {
        return fromString(tags, ",");
    }
}
