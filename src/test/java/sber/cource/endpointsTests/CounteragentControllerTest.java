package sber.cource.endpointsTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import sber.cource.controller.CounteragentController;
import sber.cource.model.CounteragentDto;
import sber.cource.service.CounteragentCrudService;
import sber.cource.service.CounteragentSearchService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тесты для CounteragentsController
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CounteragentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CounteragentController counteragentController;

    @Autowired
    private CounteragentCrudService counteragentCrudService;

    @Autowired
    private CounteragentSearchService counteragentSearchService;

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
    }

    /**
     * Очищаем БД после каждого теста
     */
    @AfterEach
    public void resetDb() {
        counteragentCrudService.deleteAll();
    }

    /**
     * Тест на привязку контроллера
     */
    @Test
    public void contextLoadsTest() {
        assertNotNull(counteragentController);
    }

    /**
     * Тест get запроса загрузки страницы с таблицей контрагентов
     *
     * @throws Exception исключение
     */
    @Test
    public void loadMainPageTest() throws Exception {
        mvc.perform(get("/counteragents"))
                .andExpect(status().isOk());
    }

    /**
     * Тест на post запрос добавления нового контрагента
     *
     * @throws Exception исключение
     */
    @Test
    @Transactional
    public void addCounteragentTest() throws Exception {
        mvc.perform(post("/counteragents")
                .contentType("application/x-www-form-urlencoded")
                .param("name", counteragentDto.getName())
                .param("inn", counteragentDto.getInn())
                .param("kpp", counteragentDto.getKpp())
                .param("accountNumber", counteragentDto.getAccountNumber())
                .param("bik", counteragentDto.getBik()))
                .andExpect(status().is3xxRedirection());
        CounteragentDto foundCounteragent = counteragentSearchService.findByName("Paul");
        assertEquals(counteragentDto.getName(), foundCounteragent.getName());
    }

    /**
     * Тест на get запрос удаления контрагента по нажатию кнопки в таблице
     *
     * @throws Exception исключение
     */
    @Test
    @Transactional
    public void deleteCounteragentByTableButtonTest() throws Exception {
        counteragentCrudService.save(counteragentDto);
        Long id = counteragentSearchService.findByName("Paul").getId();
        mvc.perform(get("/counteragents/delete/" + id)
                .contentType("application/x-www-form-urlencoded"))
                .andExpect(status().is3xxRedirection());
        assertNull(counteragentSearchService.findByName("Paul"));
    }

    /**
     * Тест на post запрос удаления контрагента по ID
     *
     * @throws Exception исключение
     */
    @Test
    @Transactional
    public void deleteCounteragentByIdTest() throws Exception {
        counteragentCrudService.save(counteragentDto);
        Long id = counteragentSearchService.findByName("Paul").getId();
        mvc.perform(post("/counteragents/delete/by_id")
                .contentType("application/x-www-form-urlencoded")
                .param("id", id.toString()))
                .andExpect(status().is3xxRedirection());
        assertNull(counteragentSearchService.findByName("Paul"));
    }

    /**
     * Тест на post запрос удаления контрагента по имени
     *
     * @throws Exception исключение
     */
    @Test
    @Transactional
    public void deleteCounteragentByNameTest() throws Exception {
        counteragentCrudService.save(counteragentDto);
        mvc.perform(post("/counteragents/delete/by_name")
                .contentType("application/x-www-form-urlencoded")
                .param("name", "Paul"))
                .andExpect(status().is3xxRedirection());
        assertNull(counteragentSearchService.findByName("Paul"));
    }

    /**
     * Тест на post запрос обновления контрагента
     *
     * @throws Exception исключение
     */
    @Test
    @Transactional
    public void updateCounteragentTest() throws Exception {
        counteragentCrudService.save(counteragentDto);
        String id = counteragentSearchService.findByName("Paul").getId().toString();
        mvc.perform(post("/counteragents/update")
                .contentType("application/x-www-form-urlencoded")
                .param("id", id)
                .param("name", "newPaul")
                .param("inn", counteragentDto.getInn())
                .param("kpp", "123456789")
                .param("accountNumber", counteragentDto.getAccountNumber())
                .param("bik", counteragentDto.getBik()))
                .andExpect(status().is3xxRedirection());
        CounteragentDto updated = counteragentSearchService.findByName("newPaul");
        assertNotNull(updated);
        assertEquals(id, updated.getId().toString());
        assertEquals("123456789", updated.getKpp());
    }

}
