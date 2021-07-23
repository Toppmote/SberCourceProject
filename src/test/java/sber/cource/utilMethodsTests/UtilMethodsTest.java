package sber.cource.utilMethodsTests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import sber.cource.model.CounteragentDto;
import sber.cource.utils.UtilMethods;

import java.util.Map;

/**
 * Тесты утильных методов
 */
public class UtilMethodsTest {

    /**
     * Тест на инициализацию словаря, ключи которого являются полями, к которым привязана ошибка,
     * а значения - сообщениями об ошибке
     */
    @Test
    public void putErrorsInMapTest() {
        CounteragentDto counteragentDto = CounteragentDto.builder()
                .name("Paul")
                .inn("7707083893")
                .kpp("222443001")
                .accountNumber("40817810502003859111")
                .bik("040173604")
                .build();
        BindingResult bindingResults = new BeanPropertyBindingResult(counteragentDto, "counteragentForm");
        bindingResults.addError(new FieldError("CounteragentDto", "name", "errInMes"));
        Map<String, String> testMap = UtilMethods.putErrorsInMap(bindingResults);
        assertTrue(testMap.containsKey("name"));
        assertEquals("errInMes", testMap.get("name"));
    }

}
