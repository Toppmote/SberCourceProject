package sber.cource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sber.cource.entity.Counteragent;
import sber.cource.entity.CounteragentForm;
import sber.cource.service.CounteragentCrudService;
import sber.cource.utils.InputConstants;

import java.util.List;

/**
 * Crud-контроллер для БД контрагентов
 */
@RestController
public class CounteragentsController {

    @Autowired
    private CounteragentCrudService counteragentCrudService;

    /**
     * Get-запрос загрузки страницы контрагентов.
     *
     * @return объект для передачи значений на страницу .jsp
     */
    @GetMapping("/counteragents")
    public ModelAndView getAllCounteragents() {
        List<Counteragent> counteragentList = counteragentCrudService.findAll();
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
    @PostMapping("/counteragents")
    public ModelAndView addCounteragent(CounteragentForm counteragentForm) {
        Counteragent newCounteragent = Counteragent.from(counteragentForm);
        counteragentCrudService.save(newCounteragent);
        return new ModelAndView("redirect:/counteragents");
    }

    /**
     * Get-запрос удаления контрагента. Запрашивается через кнопку удаления в таблице
     * @param id ID контрагента, которого необходимо удалить
     * @return объект для перенаправления на страницу контрагентов
     */
    @GetMapping("/counteragents/delete/{id}")
    public ModelAndView deleteCounteragentById(@PathVariable("id") long id) {
        counteragentCrudService.deleteById(id);
        return new ModelAndView("redirect:/counteragents");
    }

    /**
     * Post-запрос удаления контрагента как по ID, так и по имени
     * @param counteragentForm данные контрагента
     * @return объект для перенаправления на страницу контрагентов
     */
    @PostMapping("/counteragents/delete")
    public ModelAndView deleteCounteragent(CounteragentForm counteragentForm) {
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
    @PostMapping("/counteragents/update")
    public ModelAndView updateCounteragent(CounteragentForm counteragentForm) {
        counteragentCrudService.update(counteragentForm);
        return new ModelAndView("redirect:/counteragents");
    }

//    @RequestMapping(path = "/test", method = RequestMethod.GET)
//    public ModelAndView loadTestPage() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("test");
//        return modelAndView;
//    }

}
