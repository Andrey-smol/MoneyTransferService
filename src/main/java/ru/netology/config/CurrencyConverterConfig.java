package ru.netology.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.netology.converter.CurrencyConverter;
import ru.netology.converter.CurrencyConverterImpl;

@Configuration
public class CurrencyConverterConfig {
    @Bean
    public CurrencyConverter currencyConverter(RestTemplate restTemplate){
        return new CurrencyConverterImpl(restTemplate);
    }
    @Bean
    public RestTemplate restTemplate(){

        return new RestTemplate();
    }
}
