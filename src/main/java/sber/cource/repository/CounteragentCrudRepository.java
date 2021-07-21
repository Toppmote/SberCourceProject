package sber.cource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sber.cource.entity.Counteragent;

import java.util.List;
import java.util.Optional;

/**
 * Crud-репозиторий для взаимодействия контрагентами
 */
public interface CounteragentCrudRepository extends JpaRepository<Counteragent, Long> {
    /**
     * Метод удаления контрагента по имени
     * @param name имя контрагента для удаления
     */
    void deleteByName(String name);

    /**
     * Метод поиска контрагента по имени
     * @param name имя контрагента для поиска
     */
    Optional<Counteragent> findCounteragentByName(String name);

    /**
     * Метод поиска контрагента по БИК + номеру счёта
     * @param accountNumber номер счёта
     * @param bik БИК
     * @return список найденных контрагентов
     */
    Optional<List<Counteragent>> findCounteragentsByAccountNumberAndBik(String accountNumber, String bik);
}
