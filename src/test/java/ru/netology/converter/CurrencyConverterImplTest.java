package ru.netology.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.stream.Stream;

public class CurrencyConverterImplTest {
    private RestTemplate restTemplate;
    private CurrencyConverter converter;

    public static Stream<Arguments> convertCurrency() {
        return Stream.of(
                Arguments.of(10000.0, "RUB", "USD", Optional.of(121.65450121654501)),
                Arguments.of(10000.0, "EUR", "RUB", Optional.of(685000.0000000001)),
                Arguments.of(10000.0, "RUB", "CNY", Optional.of(1107.0559610705595))
        );
    }

    @BeforeEach
    public void init(){
        restTemplate = Mockito.mock(RestTemplate.class);
        converter = new MyCurrencyConverterImpl(restTemplate);
    }

    @ParameterizedTest
    @MethodSource
    public void convertCurrency(Double amount, String fromCurrency, String toCurrency, Optional<Double> expected){
        Optional<Double> result = converter.convertCurrency(amount, fromCurrency, toCurrency);
        Assertions.assertEquals(expected, result);
    }

}
