package ru.netology.model;

/**
 * Интерфейс для создания паттерна Builder для объекта CardEntity.
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
public interface CardEntityBuilder {
    CardEntityBuilder setCurrency(String currency);
    CardEntityBuilder setCardNumber(String cardNumber);
    CardEntityBuilder setCardValidTill(String cardValidTill);
    CardEntityBuilder setCardCVV(String cardCVV);
    CardEntityBuilder setBalance(int balance);
    CardEntity build();
}
