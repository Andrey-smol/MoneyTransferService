package ru.netology.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * Этот класс хранит данные о карте
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class CardEntity {
    /**
     * Код валюты.
     */
    private Currency currency;
    /**
     * Номер карты.
     */
    private String cardNumber;
    /**
     * Срок действия карты
     */
    private String cardValidTill;
    /**
     * CVV номер.
     */
    private String cardCVV;
    /**
     * Сумма на карте.
     */
    private int balance;

    public CardEntity(CardEntity other){
        this.currency = other.currency;
        this.cardNumber = other.cardNumber;
        this.cardValidTill = other.cardValidTill;
        this.cardCVV = other.cardCVV;
        this.balance = other.balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardEntity entity = (CardEntity) o;
        return Objects.equals(cardNumber, entity.cardNumber) && Objects.equals(cardValidTill, entity.cardValidTill) && Objects.equals(cardCVV, entity.cardCVV);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, cardValidTill, cardCVV);
    }
}
