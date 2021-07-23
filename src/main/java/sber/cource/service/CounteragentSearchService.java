package sber.cource.service;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.cource.model.CounteragentDto;
import sber.cource.entity.CounteragentEntity;
import sber.cource.dao.CounteragentCrudRepository;

import java.util.List;

/**
 * Сервис для работы с поиском контрагентов
 */
@Slf4j
@Service
public class CounteragentSearchService {

    @Autowired
    private CounteragentCrudRepository counteragentCrudRepository;

    @Autowired
    private MapperFacade mapperFacade;

    /**
     * Достать из БД всех контрагентов
     *
     * @return список контрагентов
     */
    public List<CounteragentDto> findAll() {
        List<CounteragentEntity> counteragentEntities = counteragentCrudRepository.findAll();
        List<CounteragentDto> mappedDtoList = mapperFacade.mapAsList(counteragentEntities, CounteragentDto.class);
        log.info("FIND ALL METHOD DONE WITH ORIKA MAPPING");
        return mappedDtoList;
    }

    /**
     * Поиск контрагента по имени
     *
     * @param name имя
     * @return Экземпляр контрагента если он найден, null если не найден
     */
    public CounteragentDto findByName(String name) {
        CounteragentEntity foundEntity = counteragentCrudRepository.findCounteragentEntityByName(name).orElse(null);
        CounteragentDto mappedDto = mapperFacade.map(foundEntity, CounteragentDto.class);
        log.info("FIND BY NAME METHOD DONE WITH ORIKA MAPPING");
        return mappedDto;
    }

    /**
     * Поиск контрагентов по номеру счёта и БИК
     *
     * @param bik       БИК
     * @param accNumber номер счёта
     * @return Список контрагентов если
     */
    public CounteragentDto findByBikAndAccNumber(String bik, String accNumber) {
        CounteragentEntity foundEntity = counteragentCrudRepository
                .findCounteragentEntityByAccountNumberAndBik(accNumber, bik).orElse(null);
        CounteragentDto mappedDto = mapperFacade.map(foundEntity, CounteragentDto.class);
        log.info("FIND BY BIK AND ACC_NUMBER METHOD DONE WITH ORIKA MAPPING");
        return mappedDto;
    }

}
