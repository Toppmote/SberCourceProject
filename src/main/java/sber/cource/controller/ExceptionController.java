package sber.cource.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Контроллер для обработки исключений
 */
@RestControllerAdvice
@Slf4j
@Api(tags = "Exception контроллер для таблицы контрагентов")
public class ExceptionController {

    /**
     * Метод обработки исключений при переходе на несуществующую страницу
     *
     * @param request Http реквест
     * @param e       Объект ошибки
     * @return объект для перенаправления на страницу обработки ошибки
     */
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Страница не найдена")
    })
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleError(HttpServletRequest request, Exception e) {
        log.error("Request: " + request.getRequestURL() + " raised " + e);
        return new ModelAndView("error");
    }
}
