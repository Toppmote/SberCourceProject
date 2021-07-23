package sber.cource.validation.annotations;

import sber.cource.validation.validators.AccNumberAndBikCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Аннотация для валидации пары БИК + номер счёта
 */
@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = AccNumberAndBikCheckValidator.class)
public @interface AccNumberAndBikCheck {

    String message() default "{AccNumberAndBikCheck.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
