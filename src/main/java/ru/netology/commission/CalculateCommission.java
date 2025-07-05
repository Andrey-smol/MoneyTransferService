package ru.netology.commission;

import ru.netology.model.Currency;

public class CalculateCommission {
    private static final double COMMISSION_RUR = 1;
    private static final double COMMISSION = 0.5;
    public static double calculate(double amount, Currency currency){

        return amount * (currency.getCode().equals(Currency.RUB.getCode()) ? COMMISSION_RUR : COMMISSION) / 100;
    }
}
