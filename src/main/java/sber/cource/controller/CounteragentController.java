package sber.cource.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sber.cource.model.CounteragentDto;
import sber.cource.service.CounteragentCrudService;
import sber.cource.service.CounteragentSearchService;
import sber.cource.utils.ErrorValue;
import sber.cource.utils.InputConstants;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static sber.cource.utils.UtilMethods.putErrorsInMap;

/**
 * Контроллер для добавления, удаления и редактирования контрагентов
 */
@Slf4j
@RestController
@RequestMapping("counteragents")
@Api(tags = "Crud контроллер для таблицы контрагентов")
public class CounteragentController {

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
    @ApiOperation(value = "Загрузить страницу с таблицей контрагентов",
            notes = "Данный метод получает всех контрагентов из базы и передает их на страницу с таблицей контрагентов." +
                    "Также в этом методе передаются параметры для вывода ошибок и констант для форм")
    @GetMapping("")
    public ModelAndView loadMainPage(Model model) {
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
        log.info("OPENED MAIN PAGE WITH ALL PARAMETERS");
        return modelAndView;
    }

    /**
     * Post-запрос добавления нового контрагента
     *
     * @param counteragentForm данные контрагента
     * @return объект для перенаправления на страницу контрагентов
     */
    @ApiOperation(value = "Сохранить нового контрагента",
            notes = "Данный метод сохраняет нового контрагента, если введены корректные данные. "
                    + "При вооде некорректных данных в форме появятся ошибки соответствующие полю")
    @PostMapping("")
    public ModelAndView addCounteragent(@ModelAttribute("counteragentForm") @Valid CounteragentDto counteragentForm,
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
    @ApiOperation(value = "Удалить контрагента по нажатию на кнопку в таблице")
    @GetMapping("/delete/{id}")
    public ModelAndView deleteCounteragentByTableButton(@PathVariable("id") long id) {
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
    @ApiOperation(value = "Удалить контрагента по ID или наименованию",
            notes = "Данный метод позволяет удалить контрагента по ID или по наименованию в зависимости от того," +
                    "от какой формы был получен запросю Если контрагента с данным параметром не найдено, " +
                    "появится предупреждение")
    @PostMapping("/delete/{field}")
    public ModelAndView deleteCounteragent(CounteragentDto counteragentForm, @PathVariable String field) {
        log.info("POST - /counteragents/delete/" + field + "\tENTERED DELETE METHOD");
        if (field.equals("by_id")) {
            if (counteragentCrudService.deleteById(counteragentForm.getId()))
                log.info("DELETE BY ID");
            else {
                errorMessages.put("deleteById", "Контрагент с введённым ID не найден");
                error = ErrorValue.DELETE_BY_ID_ERRORS.toString();
                log.info("COUNTERAGENT WITH THAT ID NOT FOUND");
            }
        } else if (field.equals("by_name")) {
            if (counteragentCrudService.deleteByName(counteragentForm.getName()))
                log.info("DELETE BY NAME");
            else {
                errorMessages.put("deleteByName", "Контрагент с введённым именем не найден");
                error = ErrorValue.DELETE_BY_NAME_ERRORS.toString();
                log.info("COUNTERAGENT WITH THAT NAME NOT FOUND");
            }
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
    @ApiOperation(value = "Редактирование контрагента",
            notes = "Данный метод позволяет редактировать контрагента, если были введены корректные данные. " +
                    "При вооде некорректных данных в форме появятся ошибки соответствующие полю")
    public ModelAndView updateCounteragent(@Valid CounteragentDto counteragentForm,
                                           BindingResult bindingResult) {
        log.info("POST - /counteragents/update\tENTERED UPDATE METHOD");
        BindingResult newBindingResults = new BeanPropertyBindingResult(counteragentForm, "counteragentForm");
        CounteragentDto counteragent = counteragentSearchService.findByName(counteragentForm.getName());
        if (counteragent != null) {
            log.info("COUNTERAGENT WITH " + counteragentForm.getName() + " IS FOUND");
            if (counteragent.getId().equals(counteragentForm.getId())) {
                log.info("THIS IS SAME COUNTERAGENT");
                List<ObjectError> errorsList = bindingResult.getFieldErrors().stream()
                        .filter(err -> !err.getField().equals("name")).collect(Collectors.toList());
                if (bindingResult.hasGlobalErrors())
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

}
