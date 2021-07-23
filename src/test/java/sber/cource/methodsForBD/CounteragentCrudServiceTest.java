package sber.cource.methodsForBD;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sber.cource.dao.CounteragentCrudRepository;
import sber.cource.model.CounteragentDto;
import sber.cource.service.CounteragentCrudService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CounteragentCrudServiceTest {

    @Autowired
    private CounteragentCrudService counteragentCrudService;

    @Autowired
    private CounteragentCrudRepository counteragentCrudRepository;

    private CounteragentDto counteragentDto;

    /**
     * Сохраняем тестового контрагента посде каждого
     */
    @BeforeEach
    public void createCounteragent() {
        counteragentDto = CounteragentDto.builder()
                .name("Paul")
                .inn("7707083893")
                .kpp("222443001")
                .accountNumber("40817810502003859111")
                .bik("040173604")
                .build();
        counteragentCrudService.save(counteragentDto);
    }

    /**
     * Очищаем БД после каждого теста
     */
    @AfterEach
    public void resetDb() {
        counteragentCrudRepository.deleteAll();
    }

    /**
     * Тест на привязку сервиса.
     */
    @Test
    public void contextLoadsTest() {
        assertNotNull(counteragentCrudService);
    }

    /**
     * Тест сохранения нескольких контрагентов
     */
    @Test
    @Transactional
    public void saveTest() {
        for (int i = 0; i < 3; i++)
            counteragentCrudService.save(CounteragentDto.builder()
                    .name(String.valueOf(i))
                    .inn(String.valueOf(i))
                    .kpp(String.valueOf(i))
                    .accountNumber(String.valueOf(i))
                    .bik(String.valueOf(i))
                    .build());
        assertTrue(counteragentCrudRepository.findCounteragentEntityByName("Paul").isPresent());
        assertTrue(counteragentCrudRepository.findCounteragentEntityByName("0").isPresent());
        assertTrue(counteragentCrudRepository.findCounteragentEntityByName("1").isPresent());
        assertTrue(counteragentCrudRepository.findCounteragentEntityByName("2").isPresent());
    }

    /**
     * Тест удаления контрагента по ID
     */
    @Test
    @Transactional
    public void deleteByIdTest() {
        counteragentCrudService.deleteById(counteragentCrudRepository
                .findCounteragentEntityByName("Paul").get().getId());
        assertFalse(counteragentCrudRepository.findCounteragentEntityByName("Paul").isPresent());
    }

    /**
     * Тест удаления контрагента по имени
     */
    @Test
    @Transactional
    public void deleteByNameTest() {
        counteragentCrudService.deleteByName(counteragentCrudRepository
                .findCounteragentEntityByName("Paul").get().getName());
        assertFalse(counteragentCrudRepository.findCounteragentEntityByName("Paul").isPresent());
    }

    /**
     * Тест редактирования записей контрагента
     */
    @Test
    @Transactional
    public void updateTest() {
        Long id = counteragentCrudRepository.findCounteragentEntityByName("Paul").get().getId();
        counteragentDto.setId(id);
        assertTrue(counteragentCrudRepository.findCounteragentEntityByName("Paul").isPresent());
        counteragentDto.setName("New Paul");
        counteragentCrudService.update(counteragentDto);
        assertEquals(id, counteragentCrudRepository.findCounteragentEntityByName("New Paul").get().getId());
        assertEquals("New Paul", counteragentCrudRepository.findById(id).get().getName());
    }

    /**
     * Тест метода удаления всех контрагентов
     */
    @Test
    @Transactional
    public void deleteAllTest() {
        for (int i = 0; i < 3; i++)
            counteragentCrudService.save(CounteragentDto.builder()
                    .name(String.valueOf(i))
                    .inn(String.valueOf(i))
                    .kpp(String.valueOf(i))
                    .accountNumber(String.valueOf(i))
                    .bik(String.valueOf(i))
                    .build());
        counteragentCrudService.deleteAll();
        assertFalse(counteragentCrudRepository.findCounteragentEntityByName("Paul").isPresent());
        assertFalse(counteragentCrudRepository.findCounteragentEntityByName("0").isPresent());
        assertFalse(counteragentCrudRepository.findCounteragentEntityByName("1").isPresent());
        assertFalse(counteragentCrudRepository.findCounteragentEntityByName("2").isPresent());
    }

}
