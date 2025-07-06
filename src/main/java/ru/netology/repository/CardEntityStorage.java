package ru.netology.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.netology.model.CardEntity;
import ru.netology.model.Currency;

import java.util.Objects;

/**
 * Этот класс хранит данные о карте в хранилище
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class CardEntityStorage {
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

    /**
     * Статус платёжной карт.
     */
    private StatusCardStory status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardEntityStorage entity = (CardEntityStorage) o;
        return Objects.equals(cardNumber, entity.cardNumber) && Objects.equals(cardValidTill, entity.cardValidTill) && Objects.equals(cardCVV, entity.cardCVV);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, cardValidTill, cardCVV);
    }
}
