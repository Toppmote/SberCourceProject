package sber.cource.validation.validators;

import sber.cource.validation.annotations.InnCheck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InnCheckValidator implements ConstraintValidator<InnCheck, String> {

    /**
     * Метод для валидации ИНН
     *
     * @param inn     строка с ИНН(10 или 12-значным)
     * @param context предоставляет контекстные данные и операции при применении данного валидатора
     * @return true - если поле ИНН корректно, false - если некорректно
     */
    @Override
    public boolean isValid(String inn, ConstraintValidatorContext context) {
        int innLength = inn.length();
        int[] innDigits = new int[innLength];
        try {
            for (int i = 0; i < innLength; i++)
                innDigits[i] = Character.getNumericValue(inn.charAt(i));
        } catch (Exception e) {
            return false;
        }
        return switch (innLength) {
            case 10 -> check10DigitInn(innDigits);
            case 12 -> check12DigitInn(innDigits);
            default -> false;
        };
    }

    /**
     * Метод проверки ИНН с 10 знаками
     *
     * @param inn 10-значный ИНН
     * @return true - если ИНН корректен, false - если некорректен
     */
    private static boolean check10DigitInn(int[] inn) {
        return inn[9] == computeInnControlSum(inn, 0) % 11 % 10;
    }

    /**
     * Метод проверки ИНН с 12 знаками
     *
     * @param inn 12-ти значный ИНН
     * @return true - если ИНН корректен, false - если некорректен
     */
    private static boolean check12DigitInn(int[] inn) {
        int controlSum1 = ((inn[0] * 7 + computeInnControlSum(inn, 1)) % 11) % 10;
        int controlSum2 = ((inn[0] * 3 + inn[1] * 7 + computeInnControlSum(inn, 2)) % 11) % 10;
        return inn[10] == controlSum1 && inn[11] == controlSum2;
    }

    /**
     * Метод для подсчёта части контрольной суммы. В подсчёте разных контрольных сумм есть общая часть,
     * начинающаяся с определённого индекса. Для 10-ти значного ИНН данный метод подсчитает всю сумму.
     * Для 12-ти значного ИНН смещение для первой контрольной суммы будет 1, для второй - 2.
     *
     * @param inn строка с ИНН(10 или 12-значным)
     * @return часть контрольной суммы
     */
    private static int computeInnControlSum(int[] inn, int index) {
        final int[] coefficients = {2, 4, 10, 3, 5, 9, 4, 6, 8};
        int controlSum = 0;
        for (int i = 0; i < 9; i++)
            controlSum += coefficients[i] * inn[i + index];
        return controlSum;
    }

}
