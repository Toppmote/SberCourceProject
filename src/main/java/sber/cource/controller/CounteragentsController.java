package sber.cource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sber.cource.entity.Counteragent;
import sber.cource.entity.CounteragentForm;
import sber.cource.service.CounteragentCrudService;
import sber.cource.utils.InputConstants;

import java.util.List;

@RestController
public class CounteragentsController {

    @Autowired
    private CounteragentCrudService counteragentService;

    /**
     * Метод при загрузке страницы контрагентов.
     *
     * @return интерфейс для передачи значений на страницу .jsp
     */
    @GetMapping("/counteragents")
    public ModelAndView getAllCounteragents() {
        List<Counteragent> counteragentList = counteragentService.findAll();
        ModelAndView modelAndView = new ModelAndView("counteragentsPage");
        modelAndView.addObject("counteragentsList", counteragentList);
        modelAndView.addObject("constants", new InputConstants());
        return modelAndView;
    }

    @PostMapping("/counteragents")
    public ModelAndView addCounteragent(CounteragentForm counteragentForm) {
        Counteragent newCounteragent = Counteragent.from(counteragentForm);
        counteragentService.save(newCounteragent);
        ModelAndView modelAndView = new ModelAndView("counteragentsPage");
        List<Counteragent> counteragentList = counteragentService.findAll();
        modelAndView.addObject("counteragentsList", counteragentList);
        modelAndView.addObject("constants", new InputConstants());
        return modelAndView;
    }

    @GetMapping("/counteragents/delete/{id}")
    public ModelAndView deleteCounteragentById(@PathVariable("id") long id) {
        counteragentService.deleteById(id);
        return new ModelAndView("counteragentsPage");
    }

    @PostMapping("/counteragents/delete")
    public ModelAndView deleteCounteragent(CounteragentForm counteragentForm) {
        System.out.print("ff");
        if (counteragentForm.getId() != null)
            counteragentService.deleteById(counteragentForm.getId());
        else
            counteragentService.deleteByName(counteragentForm.getName());
        return new ModelAndView("counteragentsPage");
    }

    @PostMapping("/counteragents/update")
    public ModelAndView updateCounteragent(CounteragentForm counteragentForm) {
        counteragentService.update(counteragentForm);
        return new ModelAndView("counteragentsPage");
    }

//    @RequestMapping(path = "/test", method = RequestMethod.GET)
//    public ModelAndView loadTestPage() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("test");
//        return modelAndView;
//    }

}
