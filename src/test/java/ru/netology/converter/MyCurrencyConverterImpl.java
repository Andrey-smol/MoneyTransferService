package ru.netology.converter;

import org.springframework.web.client.RestTemplate;
import java.util.Map;

public class MyCurrencyConverterImpl extends CurrencyConverterImpl{
    public MyCurrencyConverterImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }
    @Override
    protected void updateDataConverter(){
        CurrencyResponse response = new CurrencyResponse("USD", 10789400L,
                Map.of("USD", 1.0,
                        "RUB", 82.2,
                        "EUR",  1.2,
                        "CNY",  9.1));
        super.setResponse(response);
    }
}
