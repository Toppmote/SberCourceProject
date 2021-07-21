package sber.cource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sber.cource.entity.Counteragent;
import sber.cource.models.CounteragentDto;
import sber.cource.repository.CounteragentCrudRepository;
import java.util.Optional;

/**
 * Сервис для работы с БД контрагентов
 */
@Service
public class CounteragentCrudService {

    @Autowired
    private CounteragentCrudRepository counteragentRepository;

    /**
     * Сохранить контрагента
     *
     * @param counteragent форма с данными контрагента
     */
    public void save(Counteragent counteragent) {
        counteragentRepository.save(counteragent);
    }

    /**
     * Удалить контрагента по ID
     *
     * @param id ID для удаления
     */
    @Transactional
    public void deleteById(long id) {
        Optional<Counteragent> counteragentEntity = counteragentRepository.findById(id);
        if (counteragentEntity.isPresent())
            counteragentRepository.deleteById(id);
    }

    /**
     * Удалить контрагента по имени
     *
     * @param name имя для удаления
     */
    @Transactional
    public void deleteByName(String name) {
        Optional<Counteragent> counteragentEntity = counteragentRepository.findCounteragentByName(name);
        if (counteragentEntity.isPresent())
            counteragentRepository.deleteByName(name);
    }

    /**
     * Обновить информацию о контрагенте
     *
     * @param counteragentForm Форма данных контрагента
     */
    @Transactional
    public void update(CounteragentDto counteragentForm) {
        Optional<Counteragent> counteragentEntity = counteragentRepository.findById(counteragentForm.getId());
        if (counteragentEntity.isPresent()) {
            Counteragent editedCounteragent = counteragentEntity.get();
            editedCounteragent.setName(counteragentForm.getName());
            editedCounteragent.setInn(counteragentForm.getInn());
            editedCounteragent.setKpp(counteragentForm.getKpp());
            editedCounteragent.setAccountNumber(counteragentForm.getAccountNumber());
            editedCounteragent.setBik(counteragentForm.getBik());
            counteragentRepository.save(editedCounteragent);
        }
    }

}
