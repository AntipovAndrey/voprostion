package ru.voprostion.app.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role extends BaseModel {
    @NotNull
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private Set<User> users = createSet();

    public Role(String name) {
        this.name = name;
    }

    public void addUser(User user) {
        Objects.requireNonNull(user);
        users.add(user);
    }
}
