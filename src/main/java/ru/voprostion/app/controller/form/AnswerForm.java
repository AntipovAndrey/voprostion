package ru.voprostion.app.controller.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AnswerForm extends BaseForm {
    @NotNull
    @Length(min = 3)
    private String answer;
}
