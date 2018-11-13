package ru.voprostion.app.controller.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.voprostion.app.validation.ListSize;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class QuestionForm extends BaseForm {
    @NotNull
    @Length(min = 5, max = 140)
    private String question;
    @NotNull
    @Length(min = 2)
    @ListSize(min = 2)
    private String tags;
}
