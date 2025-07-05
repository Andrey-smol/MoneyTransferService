package ru.netology.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.netology.model.Currency;

import java.util.Optional;

@Slf4j
public class CurrencyConverterImpl implements CurrencyConverter{
    private static final String API_KEY = "d0dbf4df77583920069900ba";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD";
    private final RestTemplate restTemplate;
    private static final long TIME_SEC_UPDATE = 86400L;

    private CurrencyResponse response;

    public CurrencyConverterImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<Double> convertCurrency(Double amount, String fromCurrency, String toCurrency) {
        updateDataConverter();
        if(response == null) {
            return Optional.empty();
        }
        Double rate = 1.0;
        //если валюта не USD
        if(!fromCurrency.equals(Currency.USD.getCode())){
            //надо сначало перевести из fromCurrency в USD, а потом из USD в toCurrency
            if(response.getConversion_rates().containsKey(fromCurrency)){
                rate = response.getConversion_rates().get(fromCurrency);
                amount = amount / rate;
                if (response.getConversion_rates().containsKey(toCurrency)) {
                    rate = response.getConversion_rates().get(toCurrency);
                    amount = amount * rate;
                    return Optional.of(amount);
                }
            }
            return Optional.empty();
        }
        return Optional.of(amount);
    }

    protected void updateDataConverter(){
        long currentTimeInSeconds = System.currentTimeMillis() / 1000;
        if(response == null
                || response.getTime_last_update_unix() == 0
                || (currentTimeInSeconds - response.getTime_last_update_unix()) > TIME_SEC_UPDATE) {
            try {
                response = restTemplate.getForObject(API_URL, CurrencyResponse.class);
            } catch (RestClientException re) {
                log.error(re.getMessage());
            }
        }
    }

    public CurrencyResponse getResponse() {
        return response;
    }

    public void setResponse(CurrencyResponse response) {
        this.response = response;
    }
}
