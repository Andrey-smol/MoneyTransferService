package ru.netology.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.netology.model.Currency;

import java.util.Optional;

/**
 * Этот класс производит конвертацию валют,
 * имплементируя интерфейс CurrencyConverter.
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
@Slf4j
public class CurrencyConverterImpl implements CurrencyConverter {

    /**
     * Таймаут для обновления данных курсов валют.
     */
    private static final long TIME_SEC_UPDATE = 86400L;
    /**
     * Ключ для доступа к внешним сервисам предоставляющим курсы валют.
     */
    private static final String API_KEY = "d0dbf4df77583920069900ba";
    /**
     * Адрес ресурса в интернете.
     */
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD";
    /**
     * Ссылка на класс для взаимодействия с RESTful веб-сервисами.
     */
    private final RestTemplate restTemplate;
    /**
     * Ссылка на объект CurrencyResponse в котором хранятся данные полученные с внешнего сервиса.
     */
    private CurrencyResponse response;

    /**
     * Конструктор.
     *
     * @param restTemplate класс для взаимодействия с RESTful веб-сервисами
     */
    public CurrencyConverterImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Метод производит конвертацию валют
     *
     * @param amount       входные данные которые надо конвертировать
     * @param fromCurrency тип валюты входных данных
     * @param toCurrency   тип валюты в которую надо конвертировать
     * @return конвертированные данные обёрнутые в Optional<Double>
     * если конвертация не возможно то возвращается Optional.empty()
     * @throws IllegalArgumentException если одно из чисел отрицательное
     */
    @Override
    public Optional<Double> convertCurrency(Double amount, String fromCurrency, String toCurrency) {
        updateDataConverter();
        if (response == null) {
            return Optional.empty();
        }
        Double rate = 1.0;
        //если валюта не USD
        if (!fromCurrency.equals(Currency.USD.getCode())) {
            //надо сначало перевести из fromCurrency в USD, а потом из USD в toCurrency
            if (response.getConversion_rates().containsKey(fromCurrency)) {
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

    /**
     * Метод обновления данных о курсе валют
     *
     * @return
     */
    protected void updateDataConverter() {
        long currentTimeInSeconds = System.currentTimeMillis() / 1000;
        if (response == null
                || response.getTime_last_update_unix() == 0
                || (currentTimeInSeconds - response.getTime_last_update_unix()) > TIME_SEC_UPDATE) {
            try {
                response = restTemplate.getForObject(API_URL, CurrencyResponse.class);
            } catch (RestClientException re) {
                log.error(re.getMessage());
            }
        }
    }

    /**
     * Getter на объект CurrencyResponse в котором хранятся данные полученные с внешнего сервиса.
     *
     * @return возвращает ссылку на объект CurrencyResponse
     */
    public CurrencyResponse getResponse() {
        return response;
    }

    /**
     * Setter для объекта CurrencyResponse
     *
     * @param response
     * @return
     */
    public void setResponse(CurrencyResponse response) {
        this.response = response;
    }
}
