package ru.netology.model;

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
