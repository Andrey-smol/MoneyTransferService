package ru.netology.repository;

import org.springframework.stereotype.Component;
import ru.netology.model.CardEntity;
import ru.netology.model.Currency;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс хранилище платёжных карт
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
@Component
public class CardStorageImpl implements CardStorage {
    private final Map<String, CardEntityStorage> cardList = new ConcurrentHashMap<>();


    public CardStorageImpl() {
        cardList.put("1234567812345678",
                new CardEntityStorage(Currency.RUB, "1234567812345678", "12/30", "235", 500000,
                        StatusCardStory.ACTIVE));
        cardList.put("2341567812345678",
                new CardEntityStorage(Currency.RUB, "2341567812345678", "12/30", "230", 500000,
                        StatusCardStory.ACTIVE));
        cardList.put("3241567812345678",
                new CardEntityStorage(Currency.EUR, "3241567812345678", "12/30", "236", 500000,
                        StatusCardStory.ACTIVE));
        cardList.put("4321567812345678",
                new CardEntityStorage(Currency.USD, "4321567812345678", "12/30", "239", 500000,
                        StatusCardStory.ACTIVE));
        cardList.put("2143567812345678",
                new CardEntityStorage(Currency.CNY, "2143567812345678", "12/30", "237", 500000,
                        StatusCardStory.ACTIVE));
    }

    @Override
    public Map<String, CardEntityStorage> getCardList() {
        return cardList;
    }
}
