package ru.netology.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.netology.converter.CurrencyConverter;
import ru.netology.converter.CurrencyConverterImpl;

/**
 * Класс как источник определения бинов (объектов) в контексте приложения
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
@Configuration
public class CurrencyConverterConfig {

    /**
     * Определения бина (объекта), управляемого контейнером Spring,
     * метод возвращает объект, который должен быть зарегистрирован как бин в контексте приложения.
     *
     * @param restTemplate внедрение зависимости от RestTemplate
     * @return CurrencyConverter
     */
    @Bean
    public CurrencyConverter currencyConverter(RestTemplate restTemplate){
        return new CurrencyConverterImpl(restTemplate);
    }

    /**
     * Определения бина (объекта), управляемого контейнером Spring,
     * метод возвращает объект, который должен быть зарегистрирован как бин в контексте приложения.
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate(){

        return new RestTemplate();
    }
}
