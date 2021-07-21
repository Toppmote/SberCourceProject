package sber.cource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.cource.entity.Counteragent;
import sber.cource.repository.CounteragentCrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с поиском контрагентов
 */
@Service
public class CounteragentSearchService {

    @Autowired
    private CounteragentCrudRepository counteragentCrudRepository;

    /**
     * Достать из БД всех контрагентов
     *
     * @return список контрагентов
     */
    public List<Counteragent> findAll() {
        return (List<Counteragent>) counteragentCrudRepository.findAll();
    }

    /**
     * Поиск контрагента по имени
     * @param name имя
     * @return Экземпляр контрагента если он найден, null если не найден
     */
    public Counteragent findByName(String name) {
        return counteragentCrudRepository.findCounteragentByName(name).orElse(null);
    }

    /**
     * Поиск контрагентов по номеру счёта и БИК
     * @param bik БИК
     * @param accNumber номер счёта
     * @return Список контрагентов если
     */
    public List<Counteragent> findByBikAndAccNumber(String bik, String accNumber) {
        return counteragentCrudRepository.findCounteragentsByAccountNumberAndBik(bik, accNumber).orElse(null);
    }

}
