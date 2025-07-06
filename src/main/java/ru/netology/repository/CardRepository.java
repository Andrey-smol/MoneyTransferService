package ru.netology.repository;

import ru.netology.model.CardEntity;

import java.util.Optional;

/**
 * Интерфейс описывающий API для объекта хранящего карты.
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
public interface CardRepository {
    /**
     * Сохранение карты если новая
     *
     * @param card сущность хранящая данные о карте
     * @return если запись осуществлена то возвращается сущность иниче null
     */
    CardEntity save(CardEntity card);

    /**
     * Поиск карты по уникальному номеру
     *
     * @param cardNumber номер запрашиваемой карты
     * @return возвращает сущность с данными карты обёрнутым в Optional
     * или если не удалось найти то Optional.empty().
     */
    Optional<CardEntity> findByCardNumber(String cardNumber);

    /**
     * Поиск карты по сущности.
     *
     * @param card сущность по которой ищем карту в хранилище
     * @return возвращает сущность с данными карты обёрнутым в Optional
     * или если не удалось найти то Optional.empty().
     */
    Optional<CardEntity> findByCardEntity(CardEntity card);

    /**
     * Обновление баланса на карте
     *
     * @param card сущность по которой ищем карту в хранилище и обновляем баланс
     * @return возвращает сущность с данными карты обёрнутым в Optional
     * или если не удалось обновить то Optional.empty().
     */
    Optional<CardEntity> updateBalance(CardEntity card);

    /**
     * Удаление карты из хранилища
     *
     * @param card сущность по которой ищем карту в хранилище
     * @return возвращает сущность с данными карты обёрнутым в Optional
     * или если не удалось удалить то Optional.empty().
     */
    Optional<CardEntity> delete(CardEntity card);

    /**
     * Поиск карты по уникальному номеру
     *
     * @param cardNumber номер запрашиваемой карты
     * @return true - если карта существует, false - если карта не найдена
     */
    boolean existsByCardNumber(String cardNumber);

}
