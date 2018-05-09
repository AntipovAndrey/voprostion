package ru.voprostion.app.domain.dto;

import lombok.Data;
import ru.voprostion.app.validation.FieldsValueMatch;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "passwordConfirmation",
                message = "Passwords do not match!"
        )
})
public class UserDto {
    @NotNull
    private String name;
    @Size(min = 4)
    private String password;
    private String passwordConfirmation;
}
