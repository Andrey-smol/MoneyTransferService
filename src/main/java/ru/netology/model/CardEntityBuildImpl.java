package ru.netology.model;

import java.util.Arrays;
import java.util.Optional;

public class CardEntityBuildImpl implements CardEntityBuilder{
    private Currency currency = Currency.RUB;
    private String cardNumber;
    private String cardValidTill;
    private String cardCVV;
    private int balance = 0;

    @Override
    public CardEntityBuilder setCurrency(String currency) {
        Optional<Currency> c = Arrays.stream(Currency.values()).filter(value->value.getCode().equals(currency)).findFirst();
        if(c.isPresent()){
            this.currency = c.get();
        }
        return this;
    }

    @Override
    public CardEntityBuilder setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    @Override
    public CardEntityBuilder setCardValidTill(String cardValidTill) {
        this.cardValidTill = cardValidTill;
        return this;
    }

    @Override
    public CardEntityBuilder setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
        return this;
    }

    @Override
    public CardEntityBuilder setBalance(int balance) {
        this.balance = balance;
        return this;
    }

    @Override
    public CardEntity build() {
        return new CardEntity(currency, cardNumber, cardValidTill, cardCVV, balance);
    }
}
