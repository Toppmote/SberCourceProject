package sber.cource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import sber.cource.entity.Counteragent;
import sber.cource.entity.CounteragentForm;
import sber.cource.service.CounteragentSearchService;
import sber.cource.utils.InputConstants;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CounteragentsSearchController {

    @Autowired
    private CounteragentSearchService counteragentSearchService;

    @PostMapping("/counteragents/{field}")
    public ModelAndView searchCounteragents(CounteragentForm counteragentForm, @PathVariable String field) {
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
