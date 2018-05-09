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
public class Role extends BaseModel {
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public void addUser(User user) {
        Objects.requireNonNull(user);
        users.add(user);
    }
}
