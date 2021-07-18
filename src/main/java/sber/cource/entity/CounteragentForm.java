package sber.cource.entity;

import lombok.Data;

/**
 * Форма для добавления нового контрагента
 */
@Data
public class CounteragentForm {
    private Long id;
    private String name;
    private String inn;
    private String kpp;
    private String accountNumber;
    private String bik;
}
