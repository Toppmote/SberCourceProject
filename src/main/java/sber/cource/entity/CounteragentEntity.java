package sber.cource.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Сущность, описывающая контрагента.
 * Связан с таблицей counteragents
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "counteragents")
public class CounteragentEntity {

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

}
