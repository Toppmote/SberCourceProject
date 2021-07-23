package sber.cource.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sber.cource.entity.CounteragentEntity;

import java.util.Optional;

/**
 * Crud-репозиторий для взаимодействия контрагентами
 */
public interface CounteragentCrudRepository extends JpaRepository<CounteragentEntity, Long> {
    /**
     * Метод удаления контрагента по имени
     *
     * @param name имя контрагента для удаления
     */
    void deleteByName(String name);

    /**
     * Метод поиска контрагента по имени
     *
     * @param name имя контрагента для поиска
     */
    Optional<CounteragentEntity> findCounteragentEntityByName(String name);

    /**
     * Метод поиска контрагента по БИК + номеру счёта
     *
     * @param accountNumber номер счёта
     * @param bik           БИК
     * @return список найденных контрагентов
     */
    Optional<CounteragentEntity> findCounteragentEntityByAccountNumberAndBik(String accountNumber, String bik);
}
