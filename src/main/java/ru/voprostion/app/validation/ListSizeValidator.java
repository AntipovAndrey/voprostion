package ru.voprostion.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ListSizeValidator implements ConstraintValidator<ListSize, String> {

    private ListSize listSize;

    @Override
    public void initialize(ListSize constraintAnnotation) {
        listSize = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        final String[] strings = value.split(listSize.separator());

        if (strings.length > listSize.max() || strings.length < listSize.min()) {
            return false;
        }

        return true;
    }
}
