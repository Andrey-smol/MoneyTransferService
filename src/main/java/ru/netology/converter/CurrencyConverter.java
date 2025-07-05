package ru.netology.converter;

import java.util.Optional;

public interface CurrencyConverter {
    public Optional<Double> convertCurrency(Double amount, String fromCurrency, String toCurrency);
}
