package ru.voprostion.app.domain.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
@Data
public abstract class BaseModel implements Comparable<BaseModel> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Override
    public int compareTo(BaseModel other) {
        return Comparator.comparingLong(BaseModel::getId)
                .compare(this, other);
    }

    protected <T extends BaseModel> Set<T> createSet() {
        return new HashSet<>();
    }
}
