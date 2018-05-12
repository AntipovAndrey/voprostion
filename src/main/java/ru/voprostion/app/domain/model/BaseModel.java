package ru.voprostion.app.domain.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
@Data
public abstract class BaseModel implements Comparable<BaseModel> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private java.util.Date dateCreated = Calendar.getInstance().getTime();

    @Override
    public int compareTo(BaseModel other) {
        return Comparator.comparingLong(BaseModel::getId)
                .compare(this, other);
    }

    protected <T extends BaseModel> Set<T> createSet() {
        return new HashSet<>();
    }
}
