package ru.netology.model;

import java.util.Arrays;
import java.util.Optional;

public class Amount {
    private Currency currency;
    private int value;

    public Amount(Currency currency, int value) {
        this.currency = currency;
        this.value = value;
    }

    public String getCurrency() {
        return currency.getCode();
    }

    public void setCurrency(String currency) {
        Optional<Currency> c = Arrays.stream(Currency.values()).filter(value->value.getCode().equals(currency)).findFirst();
        if(c.isPresent()){
            this.currency = c.get();
            return;
        }
        throw new IllegalArgumentException();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
