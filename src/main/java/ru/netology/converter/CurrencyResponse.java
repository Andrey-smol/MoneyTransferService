package ru.netology.converter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class CurrencyResponse {
    private String base_code;
    private long time_last_update_unix;
    private Map<String, Double> conversion_rates;

}
