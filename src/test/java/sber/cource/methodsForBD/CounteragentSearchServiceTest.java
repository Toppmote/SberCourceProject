package sber.cource.methodsForBD;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sber.cource.model.CounteragentDto;
import sber.cource.service.CounteragentCrudService;
import sber.cource.service.CounteragentSearchService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CounteragentSearchServiceTest {

    @Autowired
    private CounteragentCrudService counteragentCrudService;

    @Autowired
    private CounteragentSearchService counteragentSearchService;

    private CounteragentDto counteragentForTest;

    /**
     * Сохраняем тестового контрагента посде каждого
     */
    @BeforeEach
    public void createCounteragent() {
        counteragentForTest = CounteragentDto.builder()
                .name("Paul")
                .inn("7707083893")
                .kpp("222443001")
                .accountNumber("40817810502003859111")
                .bik("040173604")
                .build();
    }

    /**
     * Очищаем БД после каждого теста
     */
    @AfterEach
    public void resetDb() {
        counteragentCrudService.deleteAll();
    }

    /**
     * Тест на привязку сервиса
     */
    @Test
    public void contextLoadsTest() {
        assertNotNull(counteragentSearchService);
    }

    /**
     * Тест на взятие всех записей из БД
     */
    @Test
    @Transactional
    public void findAllTest() {
        final int COUNTERAGENTS_COUNT = 11;
        for (int i = 0; i < COUNTERAGENTS_COUNT; i++) {
            CounteragentDto counteragentDto = CounteragentDto.builder()
                    .name(String.valueOf(i))
                    .inn(String.valueOf(i))
                    .kpp(String.valueOf(i))
                    .accountNumber(String.valueOf(i))
                    .bik(String.valueOf(i))
                    .build();
            counteragentCrudService.save(counteragentDto);
        }
        assertEquals(COUNTERAGENTS_COUNT, counteragentSearchService.findAll().size());
    }

    /**
     * Тест на поиск контрагента по имени
     */
    @Test
    @Transactional
    public void findByNameTest() {
        counteragentCrudService.save(counteragentForTest);
        assertNotNull(counteragentSearchService.findByName(counteragentForTest.getName()));
    }

    /**
     * Тест на поиск контрагента по паре БИК + номер счёта
     */
    @Test
    @Transactional
    public void findByBikAndAccNumberTest() {
        counteragentCrudService.save(counteragentForTest);
        assertNotNull(counteragentSearchService.findByBikAndAccNumber(counteragentForTest.getBik(),
                counteragentForTest.getAccountNumber()));
    }

}
