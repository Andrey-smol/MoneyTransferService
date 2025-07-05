package ru.netology.repository;

import ru.netology.model.CardEntity;

import java.util.Map;

public interface CardStorage {
    Map<String, CardEntity> getCardList();
}
