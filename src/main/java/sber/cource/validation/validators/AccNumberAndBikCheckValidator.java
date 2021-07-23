package sber.cource.validation.validators;

import sber.cource.model.CounteragentDto;
import sber.cource.validation.annotations.AccNumberAndBikCheck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Класс-валидатор для валидации пары номер счёта - БИК
 */
public class AccNumberAndBikCheckValidator implements ConstraintValidator<AccNumberAndBikCheck, CounteragentDto> {

    /**
     * Метод валидации пары номер счёта - БИК
     *
     * @param value   DTO контрагента, из которго берудся БИК и номер счёта
     * @param context предоставляет контекстные данные и операции при применении данного валидатора
     * @return true - если номер счёта и БИК корректны, false - если некорректы
     */
    @Override
    public boolean isValid(CounteragentDto value, ConstraintValidatorContext context) {
        String accountNumber = value.getAccountNumber();
        String bik = value.getBik();
        try {
            return bik.charAt(6) == '0' && bik.charAt(7) == '0' ?
                    computeAccountControlSum('0' + bik.substring(4, 6) + accountNumber)
                    : computeAccountControlSum(bik.substring(6) + accountNumber);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Метод подсчёта контрольного числа при проверке номера счёта
     *
     * @param accountNumber модифицированный в соответствии с типом номер счёта
     * @return результат проверки номера счёта и БИК
     */
    private static boolean computeAccountControlSum(String accountNumber) {
        final int[] coefficients = {7, 1, 3};
        int controlSum = 0;
        for (int i = 0; i < accountNumber.length(); i++)
            controlSum += Character.getNumericValue(accountNumber.charAt(i)) * coefficients[i % coefficients.length];
        return controlSum % 10 == 0;
    }


}
