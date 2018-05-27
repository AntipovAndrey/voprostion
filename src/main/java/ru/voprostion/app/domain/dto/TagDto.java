package ru.voprostion.app.domain.dto;

import lombok.Data;
import ru.voprostion.app.domain.model.Tag;

@Data
public class TagDto extends BaseDto {
    private String name;

    public TagDto(Tag tag) {
        setId(tag.getId());
        name = tag.getTagName();
    }
}
