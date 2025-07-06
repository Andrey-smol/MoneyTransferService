package ru.netology.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.Optional;

/**
 * Этот класс хранит данные о сумме перевода
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class Amount {
    /**
     * Ссылка на объект Currency - код валюты.
     */
    private Currency currency;
    /**
     * Сумма.
     */
    private int value;

    public void setCurrency(String currency) {
        Optional<Currency> c = Arrays.stream(Currency.values()).filter(value->value.getCode().equals(currency)).findFirst();
        if(c.isPresent()){
            this.currency = c.get();
            return;
        }
        throw new IllegalArgumentException();
    }
    public String getCurrency() {
        return currency.getCode();
    }
}
