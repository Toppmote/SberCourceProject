package sber.cource.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import sber.cource.validation.annotations.AccNumberAndBikCheck;
import sber.cource.validation.annotations.InnCheck;
import sber.cource.validation.annotations.NameCheck;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Форма для добавления нового контрагента
 */
@Data
@AccNumberAndBikCheck(message = "Введенный номер счёта не зарегистрирован.\nПроверьте номер счёта и БИК")
public class CounteragentDto {

    private Long id;

    @NotNull(message = "Имя контрагента не должно быть пустым")
    @NameCheck(message = "Контрагент с таким именем уже существует")
    private String name;

    @NotNull(message = "ИНН не должен быть пустым")
    @InnCheck(message = "Введённый ИНН не является корректным")
    private String inn;

    @NotNull(message = "КПП не должен быть пустым")
    @Size(min = 9, max = 9, message = "КПП содержит ровно 9 цифр")
    private String kpp;

    @NotNull(message = "Номер счёта не должен быть пустым")
    @Size(min = 20, max = 20, message = "Номер счёта содержит ровно 20 цифр")
    private String accountNumber;

    @NotNull(message = "БИК не должен быть пустым")
    @Size(min = 9, max = 9, message = "БИК содержит ровно 9 цифр")
    private String bik;
}
