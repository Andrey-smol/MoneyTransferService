package ru.netology.model;

/**
 * Это перечисление хранит валюты используемые в данном приложении
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
public enum Currency {
    RUB("RUB"),
    RUR("RUR"),
    USD("USD"),
    EUR("EUR"),
    CNY("CNY");
    private String code;

    Currency(String code) {
        this.code = code;
    }
    public String getCode(){
        return code;
    }
}
