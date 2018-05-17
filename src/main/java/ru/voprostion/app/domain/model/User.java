package ru.voprostion.app.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
public class User extends BaseModel {
    @NotNull
    @Column(unique = true)
    @Size(min = 2, max = 10)
    private String name;

    @NotNull
    private String passwordHash;

    @Transient
    @Size(min = 4, max = 16)
    private String password;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = createSet();

    @OneToMany(mappedBy = "user")
    private Set<Question> questions = createSet();
    @OneToMany(mappedBy = "user")
    private Set<Answer> answers = createSet();
    @OneToMany(mappedBy = "user")
    private Set<Vote> votes = createSet();

    public void addRole(Role role) {
        Objects.requireNonNull(role);
        if (roles.add(role)) {
            role.addUser(this);
        }
    }

    public void addQuestion(Question question) {
        Objects.requireNonNull(question);
        questions.add(question);
    }

    public void addAnswer(Answer answer) {
        Objects.requireNonNull(answer);
        answers.add(answer);
    }

    public void addVote(Vote vote) {
        Objects.requireNonNull(vote);
        votes.add(vote);
    }
}
