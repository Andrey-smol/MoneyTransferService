package ru.netology.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.netology.model.CardEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class CardRepositoryImpl implements CardRepository {
    CardStorage cardStorage;

    @Autowired
    public CardRepositoryImpl(CardStorage cardStorage) {
        this.cardStorage = cardStorage;
    }

    @Override
    public CardEntity save(CardEntity card) {

        if (cardStorage.getCardList().put(card.getCardNumber(), card) == null) {
            return card;
        }
        return null;
    }

    @Override
    public Optional<CardEntity> findByCardNumber(String cardNumber) {
        CardEntity card = cardStorage.getCardList().get(cardNumber);

        return (card == null) ? Optional.empty() : Optional.of(new CardEntity(card));
    }

    @Override
    public Optional<CardEntity> findByCardEntity(CardEntity card) {
        Optional<CardEntity> optional = findByCardNumber(card.getCardNumber());
        if(optional.isPresent()){
            return (card.equals(optional.get())) ? Optional.of(new CardEntity(optional.get())) : Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Optional<CardEntity> updateBalance(CardEntity card) {
        String key = card.getCardNumber();
        CardEntity entity = cardStorage.getCardList().get(key);
        if (entity != null) {
            return findByCardNumber(card.getCardNumber());
//            if(cardStorage.getCardList().put(key, card) == null){
//                return Optional.empty();
//            }
//            CardEntity cardEntity = cardStorage.getCardList().get(key);
//            return (cardEntity == null) ? Optional.empty() : Optional.of(new CardEntity(cardEntity));
        }
        return Optional.empty();
    }

    @Override
    public Optional<CardEntity> delete(CardEntity card) {
        String key = card.getCardNumber();
        CardEntity entity = cardStorage.getCardList().get(key);
        if (entity != null) {
            cardStorage.getCardList().remove(key);
            return Optional.of(new CardEntity(card));
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByCardNumber(String cardNumber) {

        return cardStorage.getCardList().get(cardNumber) != null;
    }
}
