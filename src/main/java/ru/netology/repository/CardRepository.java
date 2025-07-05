package ru.netology.repository;

import ru.netology.model.CardEntity;

import java.util.List;
import java.util.Optional;

public interface CardRepository {
    CardEntity save(CardEntity card);

    Optional<CardEntity> findByCardNumber(String cardNumber);
    Optional<CardEntity> findByCardEntity(CardEntity card);

    Optional<CardEntity> updateBalance(CardEntity card);

    Optional<CardEntity> delete(CardEntity card);

    boolean existsByCardNumber(String cardNumber);

}
