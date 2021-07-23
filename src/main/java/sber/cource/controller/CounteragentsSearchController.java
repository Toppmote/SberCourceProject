package sber.cource.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import sber.cource.dto.CounteragentDto;
import sber.cource.service.CounteragentSearchService;
import sber.cource.utils.InputConstants;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class CounteragentsSearchController {

    @Autowired
    private CounteragentSearchService counteragentSearchService;

    /**
     * Поиск контрагента по имени или по паре номер счёта + БИК.
     * Если один из параметров нулевой, ищем по другому параметру
     * @param counteragentForm форма с данными контрагента
     * @param field параметр поиска
     * @return объект для перенаправления на страницу результатов поиска
     */
    @PostMapping("counteragents/search/{field}")
    @ApiOperation(value = "Search for the counteragent by name or BIK/account number pair",
            notes = "This method searches counteragent by name or BIK/account number pair")
    public ModelAndView searchCounteragents(CounteragentDto counteragentForm, @PathVariable String field) {
        log.info("POST - /counteragents/search/" + field + "\tENTERED LOAD SEARCH PAGE METHOD");
        List<CounteragentDto> counteragentList = new ArrayList<>();
        if(field.equals("by_name")) {
            CounteragentDto foundCounteragent = counteragentSearchService.findByName(counteragentForm.getName());
            if(foundCounteragent != null) {
                counteragentList.add(foundCounteragent);
                log.info("FIND COUNTERAGENT BY NAME");
            }
        }
        else if(field.equals("by_bik_and_acc_number")) {
            CounteragentDto foundCounteragent = counteragentSearchService
                    .findByBikAndAccNumber(counteragentForm.getBik(), counteragentForm.getAccountNumber());
            if(foundCounteragent != null) {
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
