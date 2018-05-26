package ru.voprostion.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ListSizeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListSize {

    String message() default "Too few elements";

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String separator() default ",";

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};
}
