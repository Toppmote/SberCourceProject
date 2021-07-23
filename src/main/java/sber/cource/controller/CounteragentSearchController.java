package sber.cource.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sber.cource.model.CounteragentDto;
import sber.cource.service.CounteragentSearchService;
import sber.cource.utils.InputConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для операций поиска контрагентов
 */
@Slf4j
@RestController
@RequestMapping("counteragents")
@Api(tags = "Контроллер для поиска")
public class CounteragentSearchController {

    @Autowired
    private CounteragentSearchService counteragentSearchService;

    /**
     * Поиск контрагента по имени или по паре номер счёта + БИК.
     * Если один из параметров нулевой, ищем по другому параметру
     *
     * @param counteragentForm форма с данными контрагента
     * @param field            параметр поиска
     * @return объект для перенаправления на страницу результатов поиска
     */
    @PostMapping("/search/{field}")
    @ApiOperation(value = "Поиск контрагентов по имени или по паре БИК + номер счёта",
            notes = "Данный метод ищет контрагентов в зависимости от переданных параметров" +
                    ". Параметры передаются в форме, и, в зависимости от динамической ссылки," +
                    " происходит поиск по выбранному параметру")
    public ModelAndView searchCounteragent(CounteragentDto counteragentForm, @PathVariable String field) {
        log.info("POST - /counteragents/search/" + field + "\tENTERED LOAD SEARCH PAGE METHOD");
        List<CounteragentDto> counteragentList = new ArrayList<>();
        if (field.equals("by_name")) {
            CounteragentDto foundCounteragent = counteragentSearchService.findByName(counteragentForm.getName());
            if (foundCounteragent != null) {
                counteragentList.add(foundCounteragent);
                log.info("FIND COUNTERAGENT BY NAME");
            }
        } else if (field.equals("by_bik_and_acc_number")) {
            CounteragentDto foundCounteragent = counteragentSearchService
                    .findByBikAndAccNumber(counteragentForm.getBik(), counteragentForm.getAccountNumber());
            if (foundCounteragent != null) {
                counteragentList.add(foundCounteragent);
                log.info("FIND COUNTERAGENT BY PAIR BIK + ACC_NUMBER");
            }
        }
        ModelAndView modelAndView = new ModelAndView("search");
        modelAndView.addObject("counteragentsList", counteragentList);
        modelAndView.addObject("constants", new InputConstants());
        log.info("OPENED SEARCH PAGE WITH 2 PARAMETERS");
        return modelAndView;
    }

}
