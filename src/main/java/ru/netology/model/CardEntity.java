package ru.netology.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class CardEntity {
    private Currency currency;
    private String cardNumber;
    private String cardValidTill;
    private String cardCVV;
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
