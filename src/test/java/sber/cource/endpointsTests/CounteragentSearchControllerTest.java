package sber.cource.endpointsTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import sber.cource.controller.CounteragentSearchController;
import sber.cource.model.CounteragentDto;
import sber.cource.service.CounteragentCrudService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CounteragentSearchControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CounteragentSearchController counteragentSearchController;

    @Autowired
    private CounteragentCrudService counteragentCrudService;

    private CounteragentDto counteragentDto;

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
        counteragentCrudService.deleteAll();
    }

    /**
     * Тест на привязку контроллера
     */
    @Test
    public void contextLoadsTest() {
        assertNotNull(counteragentSearchController);
    }

    /**
     * Тест поиска контрагента по имени
     *
     * @throws Exception исключение
     */
    @Test
    public void searchCounteragentByName() throws Exception {
        mvc.perform(post("/counteragents/search/by_name")
                .contentType("application/x-www-form-urlencoded")
                .param("name", counteragentDto.getName()))
                .andExpect(status().isOk());
    }

    /**
     * Тест поиска контрагента по паре номер счёта + БИК
     *
     * @throws Exception исключение
     */
    @Test
    public void searchCounteragentByBikAndAccNumber() throws Exception {
        mvc.perform(post("/counteragents/search/by_name")
                .contentType("application/x-www-form-urlencoded")
                .param("accountNumber", counteragentDto.getAccountNumber())
                .param("bik", counteragentDto.getBik()))
                .andExpect(status().isOk());
    }

}
