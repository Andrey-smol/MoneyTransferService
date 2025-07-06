package ru.netology.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.netology.model.ConfirmOperation;
import ru.netology.model.OperationId;
import ru.netology.model.TransferMoneyDTO;
import ru.netology.service.MoneyTransferService;

/**
 * Этот класс контроллера, который обрабатывает HTTP-запросы (RESTful запросы)
 * и возвращает данные в формате JSON
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
@RestController
@RequestMapping("/")
@Slf4j
@CrossOrigin(
        value="https://serp-ya.github.io",
        methods={RequestMethod.POST},
        allowedHeaders="*"
)

public class MoneyTransferController {

    /**
     * Зависимость от RestService Controller - service
     */
    private final MoneyTransferService service;


    /**
     * Конструктор.
     *
     * @param service внедрение зависимости через параметры конструктора
     */
    @Autowired
    public MoneyTransferController(MoneyTransferService service) {
        this.service = service;
    }

    /**
     * Обработка Post запросов с маппингом "/transfer"
     *
     * @param transferMoney входные данные преобразованные из json
     * @return OperationId
     */
    @PostMapping("/transfer")
    public OperationId doTransfer(@RequestBody @Validated TransferMoneyDTO transferMoney) {
        log.info("doTransfer");
        System.out.println("doTransfer");
        return service.doTransfer(transferMoney);
    }

    /**
     * Обработка Post запросов с маппингом "/confirmOperation"
     *
     * @param confirmOperation входные данные преобразованные из json
     * @return OperationId
     */
    @PostMapping("/confirmOperation")
    public OperationId confirmOperation(@RequestBody ConfirmOperation confirmOperation) {
        return service.confirmOperation(confirmOperation);
    }

}
