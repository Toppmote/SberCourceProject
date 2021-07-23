package sber.cource.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sber.cource.validation.annotations.AccNumberAndBikCheck;
import sber.cource.validation.annotations.InnCheck;
import sber.cource.validation.annotations.NameCheck;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Dto сущности контрагента с валидацией
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Модель контрагента")
@AccNumberAndBikCheck(message = "Введенный номер счёта не зарегистрирован.\nПроверьте номер счёта и БИК")
public class CounteragentDto {

    /**
     * ID контрагента
     */
    @ApiModelProperty(
            value = "ID контрагента",
            name = "id",
            dataType = "Long",
            example = "1234")
    private Long id;

    /**
     * Наименование контрагента. Уникально
     */
    @ApiModelProperty(
            value = "Наименование",
            name = "name",
            dataType = "String",
            example = "OAO 'Восток'")
    @NotNull(message = "Имя контрагента не должно быть пустым")
    @NameCheck(message = "Контрагент с таким именем уже существует")
    private String name;

    /**
     * ИНН контрагента
     */
    @ApiModelProperty(
            value = "ИНН",
            name = "inn",
            dataType = "String",
            example = "7707083893")
    @NotNull(message = "ИНН не должен быть пустым")
    @InnCheck(message = "Введённый ИНН не является корректным")
    private String inn;

    /**
     * КПП контрагента
     */
    @ApiModelProperty(
            value = "КПП",
            name = "kpp",
            dataType = "String",
            example = "774301001")
    @NotNull(message = "КПП не должен быть пустым")
    @Pattern(regexp = "\\d{9}", message = "КПП содержит ровно 9 цифр")
    private String kpp;

    /**
     * Номер счёта контрагента
     */
    @ApiModelProperty(
            value = "Номер счёта",
            name = "accountNumber",
            dataType = "String",
            example = "40817810099910004312")
    @NotNull(message = "КПП не должен быть пустым")
    @NotNull(message = "Номер счёта не должен быть пустым")
    @Size(min = 20, max = 20, message = "Номер счёта содержит ровно 20 цифр")
    private String accountNumber;

    /**
     * БИК банка
     */
    @ApiModelProperty(
            value = "БИК",
            name = "bik",
            dataType = "String",
            example = "044525225")
    @NotNull(message = "БИК не должен быть пустым")
    @Size(min = 9, max = 9, message = "БИК содержит ровно 9 цифр")
    private String bik;

}
