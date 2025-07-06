package ru.netology.converter;

import java.util.Optional;

/**
 * Интерфейс, представляющий основные операции для работы с конвертами валют.
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
public interface CurrencyConverter {

    /**
     * Метод для конвертации валют
     *
     * @param amount входная сумма которую надо конвертировать
     * @param fromCurrency тип валюты входных данных
     * @param toCurrency   тип валюты в которую надо конвертировать
     * @return конвертированные данные обёрнутые в Optional<Double>
     *     если конвертация не возможно то возвращается Optional.empty()
     */
    Optional<Double> convertCurrency(Double amount, String fromCurrency, String toCurrency);
}
