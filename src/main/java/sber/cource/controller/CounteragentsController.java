package sber.cource.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sber.cource.entity.Counteragent;
import sber.cource.models.CounteragentDto;
import sber.cource.service.CounteragentCrudService;
import sber.cource.service.CounteragentSearchService;
import sber.cource.utils.InputConstants;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для добавления, удаления и редактирования контрагентов
 */
@RestController
@RequestMapping("counteragents")
public class CounteragentsController {

    @Autowired
    private CounteragentCrudService counteragentCrudService;

    @Autowired
    private CounteragentSearchService counteragentSearchService;

    /**
     * Get-запрос загрузки страницы контрагентов.
     *
     * @return объект для передачи значений на страницу .jsp
     */
    @ApiOperation(value = "Load page with counteragents",
            notes = "This method is loading page with counteragents table")
    @GetMapping("/")
    public ModelAndView getAllCounteragents() {
        List<Counteragent> counteragentList = counteragentSearchService.findAll();
        ModelAndView modelAndView = new ModelAndView("counteragents");
        modelAndView.addObject("counteragentsList", counteragentList);
        modelAndView.addObject("constants", new InputConstants());
        return modelAndView;
    }

    /**
     * Post-запрос добавления нового контрагента
     *
     * @param counteragentForm данные контрагента
     * @return объект для перенаправления на страницу контрагентов
     */
    @ApiOperation(value = "Save new counteragent",
            notes = "This method is saving new counteragent")
    @PostMapping("/")
    public ModelAndView addCounteragent(@Valid CounteragentDto counteragentForm) {
        Counteragent newCounteragent = Counteragent.from(counteragentForm);
        counteragentCrudService.save(newCounteragent);
        return new ModelAndView("redirect:/counteragents");
    }

    /**
     * Get-запрос удаления контрагента. Запрашивается через кнопку удаления в таблице
     * @param id ID контрагента, которого необходимо удалить
     * @return объект для перенаправления на страницу контрагентов
     */
    @ApiOperation(value = "Delete counteragent by table button click",
            notes = "This method removes counteragent when the user clicks a button in the table")
    @GetMapping("/delete/{id}")
    public ModelAndView deleteCounteragentById(@PathVariable("id") long id) {
        counteragentCrudService.deleteById(id);
        return new ModelAndView("redirect:/counteragents");
    }

    /**
     * Post-запрос удаления контрагента как по ID, так и по имени
     * @param counteragentForm данные контрагента
     * @return объект для перенаправления на страницу контрагентов
     */
    @ApiOperation(value = "Delete counteragent by id or name",
            notes = "This method removes counteragent by id or name")
    @PostMapping("/delete")
    public ModelAndView deleteCounteragent(CounteragentDto counteragentForm) {
        if (counteragentForm.getId() != null)
            counteragentCrudService.deleteById(counteragentForm.getId());
        else if (counteragentForm.getName() != null)
            counteragentCrudService.deleteByName(counteragentForm.getName());
        return new ModelAndView("redirect:/counteragents");
    }

    /**
     * Post-запрос редактирования данных контрагента
     * @param counteragentForm новые данные контрагента
     * @return объект для перенаправления на страницу контрагентов
     */
    @PostMapping("/update")
    @ApiOperation(value = "Update counteragent",
            notes = "This method updates counteragent")
    public ModelAndView updateCounteragent(@Valid CounteragentDto counteragentForm) {
        counteragentCrudService.update(counteragentForm);
        return new ModelAndView("redirect:/counteragents");
    }

    /**
     * Поиск контрагента по имени или по паре номер счёта + БИК.
     * Если один из параметров нулевой, ищем по другому параметру
     * @param counteragentForm форма с данными контрагента
     * @param field параметр поиска
     * @return объект для перенаправления на страницу результатов поиска
     */
    @PostMapping("/{field}")
    @ApiOperation(value = "Search for the counteragent by name or BIK/account number pair",
            notes = "This method searches counteragent by name or BIK/account number pair")
    public ModelAndView searchCounteragents(CounteragentDto counteragentForm, @PathVariable String field) {
        List<Counteragent> counteragentList = new ArrayList<>();
        if(field.equals("search_by_name"))
            counteragentList.add(counteragentSearchService.findByName(counteragentForm.getName()));
        else if(field.equals("search_by_bik_and_acc_number"))
            counteragentList = List.copyOf(counteragentSearchService
                    .findByBikAndAccNumber(counteragentForm.getBik(), counteragentForm.getAccountNumber()));
        ModelAndView modelAndView = new ModelAndView("search");
        modelAndView.addObject("counteragentsList", counteragentList);
        modelAndView.addObject("constants", new InputConstants());
        return modelAndView;
    }

}
