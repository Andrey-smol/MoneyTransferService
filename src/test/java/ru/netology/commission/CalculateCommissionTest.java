package ru.netology.commission;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.model.Currency;

import java.util.stream.Stream;

public class CalculateCommissionTest {

    public static Stream<Arguments> testCalculate() {
        return Stream.of(
                Arguments.of(100.0, Currency.RUB, 1.0),
                Arguments.of(200.0, Currency.RUB, 2.0),
                Arguments.of(1000.0, Currency.USD, 5.0),
                Arguments.of(150.0, Currency.RUB, 1.5),
                Arguments.of(2222.0, Currency.EUR, 11.11)
        );
    }


    @ParameterizedTest
    @MethodSource
    public void testCalculate(double amount, Currency currency, double expected){

        double result = CalculateCommission.calculate(amount, currency);
        Assertions.assertEquals(expected, result);
    }
}
