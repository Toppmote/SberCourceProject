package sber.cource.validation.validators;

import org.springframework.beans.factory.annotation.Autowired;
import sber.cource.service.CounteragentSearchService;
import sber.cource.validation.annotations.NameCheck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameCheckValidator implements ConstraintValidator<NameCheck, String> {

    @Autowired
    private CounteragentSearchService counteragentSearchService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return counteragentSearchService.findByName(value) == null;
    }
}
