package ru.netology.repository;

import ru.netology.model.CardEntity;

import java.util.Map;

/**
 * Интерфейс описывающий API для хранилища карт.
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
public interface CardStorage {
    /**
     * Возвращает ссылку на хранилище с картами Map<String, CardEntity>
     *
     * @return ссылка на Map<String, CardEntity>
     */
    Map<String, CardEntityStorage> getCardList();
}
