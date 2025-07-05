package ru.netology.model;

public interface CardEntityBuilder {
    CardEntityBuilder setCurrency(String currency);
    CardEntityBuilder setCardNumber(String cardNumber);
    CardEntityBuilder setCardValidTill(String cardValidTill);
    CardEntityBuilder setCardCVV(String cardCVV);
    CardEntityBuilder setBalance(int balance);
    CardEntity build();
}
