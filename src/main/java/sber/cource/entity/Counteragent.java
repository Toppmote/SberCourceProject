package sber.cource.entity;

import lombok.*;
import sber.cource.models.CounteragentDto;

import javax.persistence.*;

/**
 * Класс, описывающий контрагента.
 * Связан с таблицей counteragents
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "counteragents")
public class Counteragent {

    /**
     * ID контрагента.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Наименование контрагента. Уникально
     */
    @Column(name = "agent_name", unique = true, length = 20, nullable = false)
    private String name;

    /**
     * ИНН контрагента
     */
    @Column(name = "INN", length = 12, nullable = false)
    private String inn;

    /**
     * КПП контрагента
     */
    @Column(name = "KPP", length = 9, nullable = false)
    private String kpp;

    /**
     * Номер счёта контрагента
     */
    @Column(name = "account_number", length = 20, nullable = false)
    private String accountNumber;

    /**
     * БИК банка
     */
    @Column(name = "BIK", length = 9, nullable = false)
    private String bik;

    /**
     * Метод создания объекта контрагента из Data Transfer Object
     * @param counteragentDto DTO контрагента
     * @return объект контрагента
     */
    public static Counteragent from(CounteragentDto counteragentDto) {
        return Counteragent.builder()
                .name(counteragentDto.getName())
                .inn(counteragentDto.getInn())
                .kpp(counteragentDto.getKpp())
                .accountNumber(counteragentDto.getAccountNumber())
                .bik(counteragentDto.getBik())
                .build();
    }

}
