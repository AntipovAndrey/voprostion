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
public class UserDto extends BaseDto {
    @NotNull
    @Size(min = 2, max = 10)
    private String name ;
    @Size(min = 4, max = 16)
    private String password ;
    private String passwordConfirmation ;
}
