package sber.cource.validation.annotations;

import sber.cource.validation.validators.InnCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Аннотация для валидации ИНН
 */
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = InnCheckValidator.class)
public @interface InnCheck {

    String message() default "{InnCheck.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
