package sber.cource.service;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sber.cource.entity.CounteragentEntity;
import sber.cource.model.CounteragentDto;
import sber.cource.dao.CounteragentCrudRepository;

import java.util.Optional;

/**
 * Сервис для работы с БД контрагентов
 */
@Slf4j
@Service
public class CounteragentCrudService {

    @Autowired
    private CounteragentCrudRepository counteragentRepository;

    @Autowired
    private MapperFacade mapperFacade;

    /**
     * Сохранить контрагента
     *
     * @param counteragent форма с данными контрагента
     */
    public void save(CounteragentDto counteragent) {
        CounteragentEntity counteragentEntity = mapperFacade.map(counteragent, CounteragentEntity.class);
        counteragentRepository.save(counteragentEntity);
        log.info("SAVE METHOD DONE WITH ORIKA MAPPING");
    }

    /**
     * Удалить контрагента по ID
     *
     * @param id ID для удаления
     */
    @Transactional
    public boolean deleteById(long id) {
        Optional<CounteragentEntity> counteragentEntity = counteragentRepository.findById(id);
        if (counteragentEntity.isPresent()) {
            counteragentRepository.deleteById(id);
            log.info("DELETE BY ID METHOD DONE");
            return true;
        } else return false;
    }

    /**
     * Удалить контрагента по имени
     *
     * @param name имя для удаления
     */
    @Transactional
    public boolean deleteByName(String name) {
        Optional<CounteragentEntity> counteragentEntity = counteragentRepository.findCounteragentEntityByName(name);
        if (counteragentEntity.isPresent()) {
            counteragentRepository.deleteByName(name);
            log.info("DELETE BY NAME METHOD DONE");
            return true;
        } else return false;
    }

    /**
     * Обновить информацию о контрагенте
     *
     * @param counteragentForm Форма данных контрагента
     */
    @Transactional
    public void update(CounteragentDto counteragentForm) {
        Optional<CounteragentEntity> counteragentEntity = counteragentRepository.findById(counteragentForm.getId());
        if (counteragentEntity.isPresent()) {
            CounteragentEntity editedCounteragent = mapperFacade.map(counteragentForm, CounteragentEntity.class);
            counteragentRepository.save(editedCounteragent);
            log.info("UPDATE METHOD DONE WITH ORIKA MAPPING");
        }
    }

    /**
     * Удаление всех записей из базы данных
     */
    public void deleteAll() {
        counteragentRepository.deleteAll();
    }

}
