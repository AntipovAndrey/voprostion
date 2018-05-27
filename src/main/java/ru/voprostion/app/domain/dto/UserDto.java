package ru.voprostion.app.domain.dto;

import lombok.Data;
import ru.voprostion.app.domain.model.User;

@Data
public class UserDto extends BaseDto {
    private String name;

    public UserDto(User user) {
        setId(user.getId());
        name = user.getName();
    }
}
