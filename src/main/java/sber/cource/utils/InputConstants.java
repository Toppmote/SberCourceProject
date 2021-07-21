package sber.cource.utils;

import lombok.Getter;

/**
 * Класс с константами размеров каждого из полей контрагента для ввода в форме на сайте
 */
@Getter
public class InputConstants {
    private final int ID_MAX_VALUE = 1000;
    private final int NAME_LENGTH = 20;
    private final int INN_MAX_LENGTH = 12;
    private final int KPP_LENGTH = 9;
    private final int ACCOUNT_NUMBER_LENGTH = 20;
    private final int BIK_LENGTH = 9;
}
