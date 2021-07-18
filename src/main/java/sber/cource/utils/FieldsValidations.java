package sber.cource.utils;

/**
 * Класс с методами валидации ИНН, КПП, Номера счёта и БИК банка
 */
public class FieldsValidations {

    /**
     * Метод, для валидации всех полей контрагента
     *
     * @param name          имя
     * @param inn           ИНН
     * @param kpp           КПП
     * @param accountNumber номер счёта
     * @param bik           БИК
     * @return true - если данные контрагента корректны, false - если некорректны
     */
    public static boolean validateContragent(String name, String inn, String kpp, String accountNumber, String bik) {
        return validateInn(inn) || validateKppOrBik(kpp) ||
                validateKppOrBik(bik) || validateAccountNumber(accountNumber, bik);
    }

    /**
     * Метод для валидации ИНН
     *
     * @param inn строка с ИНН(10 или 12-значным)
     * @return true - если поле ИНН корректно, false - если некорректно
     */
    public static boolean validateInn(String inn) {
        return switch (inn.length()) {
            case 10 -> check10DigitInn(inn);
            case 12 -> check12DigitInn(inn);
            default -> false;
        };
    }

    /**
     * Метод проверки ИНН с 10 знаками
     *
     * @param inn 10-значный ИНН
     * @return true - если ИНН корректен, false - если некорректен
     */
    private static boolean check10DigitInn(String inn) {
        try {
            return (int) inn.charAt(9) == computeInnControlSum(inn);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Метод проверки ИНН с 12 знаками
     *
     * @param inn 12-ти значный ИНН
     * @return true - если ИНН корректен, false - если некорректен
     */
    private static boolean check12DigitInn(String inn) {
        try {
            int controlSum1 = (int) inn.charAt(0) * 7 + computeInnControlSum(inn.substring(1));
            int controlSum2 = (int) inn.charAt(0) * 3 + (int) inn.charAt(0) * 7 + computeInnControlSum(inn.substring(2));
            return (int) inn.charAt(10) == controlSum1 && (int) inn.charAt(11) == controlSum2;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Метод для подсчёта части контрольной суммы. В подсчёте разных контрольных сумм есть общая часть,
     * начинающаяся с определённого индекса. Для 10-ти значного ИНН данный метод подсчитает всю сумму.
     * Для 12-ти значного ИНН смещение для первой контрольной суммы будет 1, для второй - 2.
     *
     * @param inn строка с ИНН(10 или 12-значным)
     * @return часть контрольной суммы
     */
    private static int computeInnControlSum(String inn) {
        final int[] coefficients = {2, 4, 10, 3, 5, 9, 4, 6, 8};
        int controlSum = 0;
        for (int i = 0; i < 9; i++)
            controlSum += coefficients[i] * (int) inn.charAt(i);
        return (controlSum % 11) % 10;
    }

    /**
     * Метод проверки КПП или БИК
     *
     * @param field строка с КПП или БИК
     * @return true - если поле КПП или БИК корректно, false - если некорректно
     */
    public static boolean validateKppOrBik(String field) {
        return field.length() == 9;
    }

    /**
     * Проверка номера счёта банка
     *
     * @param accountNumber номер счёта
     * @param bik           БИК
     * @return true - если поле номера счёта корректно, false - если некорректно
     */
    public static boolean validateAccountNumber(String accountNumber, String bik) {
        if (accountNumber.length() != 20)
            return false;
        try {
            return bik.charAt(6) == '0' && bik.charAt(7) == '0' ?
                    checkCorrAccount(accountNumber, bik) : checkCheckingAccount(accountNumber, bik);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Метод проверки коррсчёта
     *
     * @param accountNumber номер счёта
     * @param bik           БИК
     * @return true - если номер счёта корректен, false - если некорректен
     */
    private static boolean checkCorrAccount(String accountNumber, String bik) {
        accountNumber = '0' + accountNumber + bik.substring(4, 6);
        return 0 == computeAccountControlSum(accountNumber);
    }

    /**
     * Метод проверки расчётного счёта
     *
     * @param accountNumber номер счёта
     * @param bik           БИК
     * @return true - если номер счёта корректен, false - если некорректен
     */
    private static boolean checkCheckingAccount(String accountNumber, String bik) {
        accountNumber = accountNumber + bik.substring(6);
        return 0 == computeAccountControlSum(accountNumber);
    }

    /**
     * Метод подсчёта контрольного числа при проверке номера счёта
     *
     * @param accountNumber модифицированный в соответствии с типом номер счёта
     * @return контрольное число
     */
    private static int computeAccountControlSum(String accountNumber) {
        final int[] coefficients = {3, 7, 1};
        int controlSum = 0;
        for (int i = 0; i < accountNumber.length(); i++)
            controlSum += (int) accountNumber.charAt(i) * coefficients[i % coefficients.length];
        return controlSum % 10;
    }

}
