package ru.voprostion.app.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.voprostion.app.domain.model.Tag;

@Data
public class TagDto extends BaseDto {
    private String name;

    @Override
    @JsonIgnore
    public Long getId() {
        return super.getId();
    }

    public TagDto(Tag tag) {
        setId(tag.getId());
        name = tag.getTagName();
    }
}
