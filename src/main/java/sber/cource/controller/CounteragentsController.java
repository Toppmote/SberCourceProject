package sber.cource.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sber.cource.dto.CounteragentDto;
import sber.cource.service.CounteragentCrudService;
import sber.cource.service.CounteragentSearchService;
import sber.cource.utils.ErrorValue;
import sber.cource.utils.InputConstants;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Контроллер для добавления, удаления и редактирования контрагентов
 */
@Slf4j
@RestController
@RequestMapping("counteragents")
public class CounteragentsController {

    @Autowired
    private CounteragentCrudService counteragentCrudService;

    @Autowired
    private CounteragentSearchService counteragentSearchService;

    /**
     * Массив строк с кодами ошибок при работе с контрагентами
     */
    private final String[] errorValues = Arrays.stream(ErrorValue.values())
            .map(Enum::toString)
            .toArray(String[]::new);

    /**
     * Строка для передачи ошибки на jsp страницу
     */
    private String error = ErrorValue.NO_ERRORS.toString();

    /**
     * Объект для передачи данных в модальные формы при ошибках
     */
    private CounteragentDto counteragentErrForm;

    /**
     * Словарь для передачи сообщений об ошибках в модальные формы
     */
    private Map<String, String> errorMessages = new HashMap<>();

    /**
     * Get-запрос загрузки страницы контрагентов.
     *
     * @return объект для передачи значений на страницу .jsp
     */
    @ApiOperation(value = "Load page with counteragents",
            notes = "This method is loading page with counteragents table")
    @GetMapping("")
    public ModelAndView loadMainPAge(Model model) {
        log.info("GET - /counteragents\tENTERED LOAD MAIN PAGE METHOD");
        List<CounteragentDto> counteragentList = counteragentSearchService.findAll();
        ModelAndView modelAndView = new ModelAndView("counteragents");
        if (errorMessages.isEmpty()) {
            log.info("NO ERRORS");
            error = ErrorValue.NO_ERRORS.toString();
        }
        model.addAttribute("error", error);
        model.addAttribute("counteragentsList", counteragentList);
        model.addAttribute("counteragentErrForm", counteragentErrForm);
        model.addAttribute("errorValues", errorValues);
        model.addAttribute("constants", new InputConstants());
        model.addAttribute("errorMessages", errorMessages);
        log.info("OPENED MAIN PAGE WITH 6 PARAMETERS");
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
    @PostMapping("")
    public ModelAndView addCounteragent(@Valid CounteragentDto counteragentForm,
                                        BindingResult bindingResult) {
        log.info("POST - /counteragents\tENTERED ADD METHOD");
        if (bindingResult.hasErrors()) {
            log.error("ADD FORM HAS ERRORS:");
            for (var error : bindingResult.getAllErrors()) {
                log.error(error.toString());
            }
            counteragentErrForm = counteragentForm;
            error = ErrorValue.ADD_ERRORS.toString();
            errorMessages = putErrorsInMap(bindingResult);
            log.info("REDIRECTING TO MAIN PAGE");
            return new ModelAndView("redirect:/counteragents");
        }
        counteragentCrudService.save(counteragentForm);
        log.info("ADD COMPLETED\tREDIRECTING TO MAIN PAGE");
        return new ModelAndView("redirect:/counteragents");
    }

    /**
     * Get-запрос удаления контрагента. Запрашивается через кнопку удаления в таблице
     *
     * @param id ID контрагента, которого необходимо удалить
     * @return объект для перенаправления на страницу контрагентов
     */
    @ApiOperation(value = "Delete counteragent by table button click",
            notes = "This method removes counteragent when the user clicks a button in the table")
    @GetMapping("/delete/{id}")
    public ModelAndView deleteCounteragentById(@PathVariable("id") long id) {
        log.info("GET - /counteragents/delete/" + id + "\tENTERED DELETE BY TABLE BUTTON METHOD");
        counteragentCrudService.deleteById(id);
        log.info("DELETE COMPLETED\tREDIRECTING TO MAIN PAGE");
        return new ModelAndView("redirect:/counteragents");
    }

    /**
     * Post-запрос удаления контрагента как по ID, так и по имени
     *
     * @param counteragentForm данные контрагента
     * @return объект для перенаправления на страницу контрагентов
     */
    @ApiOperation(value = "Delete counteragent by id or name",
            notes = "This method removes counteragent by id or name")
    @PostMapping("/delete/{field}")
    public ModelAndView deleteCounteragent(CounteragentDto counteragentForm, @PathVariable String field) {
        log.info("POST - /counteragents/delete/" + field + "\tENTERED DELETE METHOD");
        if (field.equals("by_id")) {
            counteragentCrudService.deleteById(counteragentForm.getId());
            log.info("DELETE BY ID");
        } else if (field.equals("by_name")) {
            counteragentCrudService.deleteByName(counteragentForm.getName());
            log.info("DELETE BY NAME");
        }
        log.info("DELETE COMPLETED\tREDIRECTING TO MAIN PAGE");
        return new ModelAndView("redirect:/counteragents");
    }

    /**
     * Post-запрос редактирования данных контрагента
     *
     * @param counteragentForm новые данные контрагента
     * @return объект для перенаправления на страницу контрагентов
     */
    @PostMapping("/update")
    @ApiOperation(value = "Update counteragent",
            notes = "This method updates counteragent")
    public ModelAndView updateCounteragent(@Valid CounteragentDto counteragentForm, BindingResult bindingResult) {
        log.info("POST - /counteragents/update\tENTERED UPDATE METHOD");
        BindingResult newBindingResults = new BeanPropertyBindingResult(counteragentForm, "counteragentForm");
        CounteragentDto counteragent = counteragentSearchService.findByName(counteragentForm.getName());
        if (counteragent != null) {
            log.info("COUNTERAGENT WITH " + counteragentForm.getName() + " IS FOUND");
            if (counteragent.getId().equals(counteragentForm.getId())) {
                log.info("THIS IS SAME COUNTERAGENT");
                List<ObjectError> errorsList = bindingResult.getFieldErrors().stream()
                        .filter(err -> !err.getField().equals("name")).collect(Collectors.toList());
                if(bindingResult.hasGlobalErrors())
                    errorsList.add(bindingResult.getGlobalError());
                for (ObjectError error : errorsList)
                    newBindingResults.addError(error);
            } else newBindingResults = bindingResult;
        } else {
            log.info("COUNTERAGENT WITH " + counteragentForm.getName() + " NOT FOUND");
            newBindingResults = bindingResult;
        }
        if (newBindingResults.hasErrors()) {
            log.error("UPDATE FORM HAS ERRORS:");
            for (var error : bindingResult.getAllErrors()) {
                log.error(error.toString());
            }
            error = ErrorValue.CHANGE_ERRORS.toString();
            counteragentErrForm = counteragentForm;
            errorMessages = putErrorsInMap(newBindingResults);
            log.info("REDIRECTING TO MAIN PAGE");
            return new ModelAndView("redirect:/counteragents");
        }
        counteragentCrudService.update(counteragentForm);
        log.info("UPDATE COMPLETED\tREDIRECTING TO MAIN PAGE");
        return new ModelAndView("redirect:/counteragents");
    }

    /**
     * Метод, возвращающий все ошибки валидации в виде словаря, где ключ - поле, а значение - сообщение об ошибке
     *
     * @param bindingResult результаты валидации
     * @return Словарь, где ключ - поле, а значение - сообщение об ошибке
     */
    private Map<String, String> putErrorsInMap(BindingResult bindingResult) {
        var errorsMap = bindingResult.getFieldErrors()
                .stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        if (bindingResult.hasGlobalErrors() &&
                (!errorsMap.containsKey("accountNumber") || !errorsMap.containsKey("bik"))) {
            var globalError = bindingResult.getGlobalError();
            errorsMap.put("accNumberAndBik", globalError.getDefaultMessage());
        }
        return errorsMap;
    }

}
