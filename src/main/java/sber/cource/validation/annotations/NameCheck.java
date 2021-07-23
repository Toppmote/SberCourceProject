package sber.cource.validation.annotations;

import sber.cource.validation.validators.InnCheckValidator;
import sber.cource.validation.validators.NameCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Аннотация для валидации уникальности имени
 */
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = NameCheckValidator.class)
public @interface NameCheck {

    String message() default "{InnCheck.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
