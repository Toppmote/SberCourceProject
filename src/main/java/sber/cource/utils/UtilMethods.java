package sber.cource.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс сервисных методов
 */ 
public class UtilMethods {

    /**
     * Метод, возвращающий все ошибки валидации в виде словаря, где ключ - поле, а значение - сообщение об ошибке
     *
     * @param bindingResult результаты валидации
     * @return Словарь, где ключ - поле, а значение - сообщение об ошибке
     */
    public static Map<String, String> putErrorsInMap(BindingResult bindingResult) {
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
