package ru.netology.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.netology.mapperDTO.Mapper;
import ru.netology.model.CardEntity;

import java.util.Optional;

/**
 * Класс который реализует интерфейс CardRepository
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
@Repository
@Slf4j
public class CardRepositoryImpl implements CardRepository {
    /**
     * Ссылка на хранилище карт.
     */
    private final CardStorage cardStorage;

    private final Mapper mapper;

    @Autowired
    public CardRepositoryImpl(CardStorage cardStorage, Mapper mapper) {

        this.cardStorage = cardStorage;
        this.mapper = mapper;
    }

    /**
     * Сохранение карты если новая
     *
     * @param card сущность хранящая данные о карте
     * @return если запись осуществлена то возвращается сущность иниче null
     */
    @Override
    public CardEntity save(CardEntity card) {
        CardEntityStorage cardEntityStorage = mapper.toEntityStorage(card);
        if (cardStorage.getCardList().put(card.getCardNumber(), cardEntityStorage) == null) {
            return card;
        }
        return null;
    }

    /**
     * Поиск карты по уникальному номеру
     *
     * @param cardNumber номер запрашиваемой карты
     * @return возвращает сущность с данными карты обёрнутым в Optional
     * или если не удалось найти то Optional.empty().
     */
    @Override
    public Optional<CardEntity> findByCardNumber(String cardNumber) {

        CardEntityStorage card = cardStorage.getCardList().get(cardNumber);
        if(card == null || card.getStatus() != StatusCardStory.ACTIVE){
            return Optional.empty();
        }
        CardEntity cardEntity = mapper.toEntityFromStorage(card);
        return Optional.of(cardEntity);
    }

    /**
     * Поиск карты по сущности.
     *
     * @param card сущность по которой ищем карту в хранилище
     * @return возвращает сущность с данными карты обёрнутым в Optional
     * или если не удалось найти то Optional.empty().
     */
    @Override
    public Optional<CardEntity> findByCardEntity(CardEntity card) {

        Optional<CardEntity> optional = findByCardNumber(card.getCardNumber());
        if(optional.isPresent()){
            CardEntityStorage cardEntityStorage = mapper.toEntityStorage(card);
            if(cardStorage.getCardList().containsValue(cardEntityStorage)){
                return optional;//(mapper.toEntityFromStorage(cardEntityStorage));
            }
        }
        return Optional.empty();
    }

    /**
     * Обновление баланса на карте
     *
     * @param card сущность по которой ищем карту в хранилище и обновляем баланс
     * @return возвращает сущность с данными карты обёрнутым в Optional
     * или если не удалось обновить то Optional.empty().
     */
    @Override
    public Optional<CardEntity> updateBalance(CardEntity card) {
        String key = card.getCardNumber();
        CardEntityStorage entity = cardStorage.getCardList().get(key);
        if(entity.getStatus() != StatusCardStory.ACTIVE) {
            return Optional.empty();
        }
        entity.setBalance(card.getBalance());
        cardStorage.getCardList().put(key, entity);

        return Optional.of(card);
    }

    /**
     * Удаление карты из хранилища
     *
     * @param card сущность по которой ищем карту в хранилище
     * @return возвращает сущность с данными карты обёрнутым в Optional
     * или если не удалось удалить то Optional.empty().
     */
    @Override
    public Optional<CardEntity> delete(CardEntity card) {
        String key = card.getCardNumber();
        CardEntityStorage entity = cardStorage.getCardList().get(key);
        if (entity != null) {
            entity.setStatus(StatusCardStory.BLOCKED);
            cardStorage.getCardList().put(key, entity);
            return Optional.of(card);
        }
        return Optional.empty();
    }

    /**
     * Поиск карты по уникальному номеру
     *
     * @param cardNumber номер запрашиваемой карты
     * @return true - если карта существует, false - если карта не найдена
     */
    @Override
    public boolean existsByCardNumber(String cardNumber) {

        return cardStorage.getCardList().get(cardNumber) != null;
    }
}
