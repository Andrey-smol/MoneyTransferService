package ru.netology;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.netology.converter.CurrencyConverter;

@SpringBootApplication
@Slf4j
public class MoneyTransferServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyTransferServiceApplication.class, args);
        log.info("Starting my application with {} args.", args.length);
    }

}
