package ru.netology.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
//import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;
import ru.netology.mapperDTO.Mapper;
import ru.netology.model.CardEntity;
import ru.netology.model.Currency;
import ru.netology.model.OperationId;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CardRepositoryImplTest {

    private final Map<String, CardEntityStorage> cardList = new ConcurrentHashMap<>();
    private CardStorage cardStorage;
    private CardRepository cardRepository;
    private Mapper mapper;

    public static Stream<Arguments> testSave() {
        return Stream.of(
                Arguments.of(new CardEntity(Currency.RUB, "1234567812345678", "10/30", "235", 500000),
                        null),
                Arguments.of(new CardEntity(Currency.CNY, "9876567812345678", "12/30", "240", 500000),
                        new CardEntity(Currency.CNY, "9876567812345678", "12/30", "240", 500000))
        );
    }

    public static Stream<Arguments> testFindByCardNumber() {
        return Stream.of(
                Arguments.of("2341567812345678",
                        Optional.of(new CardEntity(Currency.RUB, "2341567812345678", "12/30", "230", 500000))),
                Arguments.of("8574633332222222",
                        Optional.empty())
        );
    }

    public static Stream<Arguments> testFindByCardEntity() {
        return Stream.of(
                Arguments.of(new CardEntity(Currency.EUR, "3241567812345678", "12/30", "236", 500000),
                        Optional.of(new CardEntity(Currency.EUR, "3241567812345678", "12/30", "236", 500000))),
                Arguments.of(new CardEntity(Currency.EUR, "3241567812345679", "12/30", "236", 500000),
                        Optional.empty()),
                Arguments.of(new CardEntity(Currency.EUR, "3241567812345678", "12/30", "230", 500000),
                        Optional.empty())
        );
    }

    public static Stream<Arguments> testUpdateBalance() {
        return Stream.of(
                Arguments.of(new CardEntity(Currency.USD, "4321567812345678", "12/30", "239", 100),
                        Optional.of(new CardEntity(Currency.USD, "4321567812345678", "12/30", "239", 100))),
                Arguments.of(new CardEntity(Currency.USD, "0321567812345678", "12/30", "239", 100),
                        Optional.empty())
        );
    }

    public Stream<Arguments> testDelete() {
        return Stream.of(
                Arguments.of(new CardEntity(Currency.CNY, "2143567812345678", "12/30", "237", 500000),
                        Optional.of(new CardEntity(Currency.CNY, "2143567812345678", "12/30", "237", 500000))),
                Arguments.of(new CardEntity(Currency.CNY, "2143567812345677", "12/30", "237", 500000),
                        Optional.empty())
        );
    }

    public Stream<Arguments> testExistsByCardNumber() {
        return Stream.of(
                Arguments.of("4321567812345678", true),
                Arguments.of("2341567812345678", true),
                Arguments.of("1341567812345678", false)
        );
    }

    @BeforeEach
    public void init() {
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
        cardStorage = Mockito.mock(CardStorage.class);
        Mockito.when(cardStorage.getCardList()).thenReturn(cardList);
        mapper = new Mapper(); //Mockito.mock(Mapper.class);
        cardRepository = new CardRepositoryImpl(cardStorage, mapper);
    }


    @ParameterizedTest
    @MethodSource
    public void testSave(CardEntity cardEntity, CardEntity expected) {
        CardEntity result = cardRepository.save(cardEntity);
        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource
    public void testFindByCardNumber(String number, Optional<CardEntity> expected) {
        Optional<CardEntity> result = cardRepository.findByCardNumber(number);
        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource
    public void testFindByCardEntity(CardEntity card, Optional<CardEntity> expected) {

        Optional<CardEntity> result = cardRepository.findByCardEntity(card);
        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource
    public void testUpdateBalance(CardEntity card, Optional<CardEntity> expected) {

        Optional<CardEntity> result = cardRepository.findByCardEntity(card);
        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource
    public void testDelete(CardEntity card, Optional<CardEntity> expected) {

        Optional<CardEntity> result = cardRepository.delete(card);
        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource
    public void testExistsByCardNumber(String number, boolean expected) {

        boolean result = cardRepository.existsByCardNumber(number);
        Assertions.assertEquals(expected, result);
    }


}
