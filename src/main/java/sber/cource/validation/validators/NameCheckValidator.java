package sber.cource.validation.validators;

import org.springframework.beans.factory.annotation.Autowired;
import sber.cource.service.CounteragentSearchService;
import sber.cource.validation.annotations.NameCheck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Класс-валидатор для валидации уникальности имени
 */
public class NameCheckValidator implements ConstraintValidator<NameCheck, String> {

    @Autowired
    private CounteragentSearchService counteragentSearchService;

    /**
     * Метод валидации имени по уникальности
     *
     * @param value   Имя контрагента
     * @param context контекстные данные и операции при применении данного валидатора
     * @return true - если имя уникально, false - если нет
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return counteragentSearchService.findByName(value) == null;
    }
}
