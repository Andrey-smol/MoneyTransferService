package ru.netology.commission;

import ru.netology.model.Currency;


/**
 * Этот класс вычисляет комиссию переданных данных: сумма и тип валюты.
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
public class CalculateCommission {

    /**
     * Комиссия для рублей.
     */
    private static final double COMMISSION_RUR = 1;

    /**
     * Комиссия для остальной валюты.
     */
    private static final double COMMISSION = 0.5;

    /**
     * Вычисляет комиссию
     *
     * @param amount   входные данные с которых надо вычислить комиссию
     * @param currency тип валюты входных данных
     * @return вычисленная комиссия
     */
    public static double calculate(double amount, Currency currency) {

        return amount * (currency.getCode().equals(Currency.RUB.getCode()) ? COMMISSION_RUR : COMMISSION) / 100;
    }
}
