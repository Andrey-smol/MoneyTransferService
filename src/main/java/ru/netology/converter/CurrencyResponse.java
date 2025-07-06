package ru.netology.converter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * Этот класс для хранения данных полученных с сервиса курсы валют
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class CurrencyResponse {
    /**
     * Строка базового кода валюты от которой курсы остальных валют,
     * обычно USD.
     */
    private String base_code;
    /**
     * Время последнего обновления курсов в unix.
     */
    private long time_last_update_unix;
    /**
     * Коллекция Map<String, Double>, где ключ код валюты, а значение курс к базовой валюте .
     */
    private Map<String, Double> conversion_rates;

}
