package ru.netology.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.netology.model.ConfirmOperation;
import ru.netology.model.OperationId;
import ru.netology.model.TransferMoneyDTO;
import ru.netology.service.MoneyTransferService;

@RestController
@RequestMapping("/")
@Slf4j
@CrossOrigin(
        value="https://serp-ya.github.io"
        //methods={RequestMethod.POST},
        //allowedHeaders="*",
        //allowCredentials="true"
)

public class MoneyTransferController {

    private final MoneyTransferService service;

    @Autowired
    public MoneyTransferController(MoneyTransferService service) {
        this.service = service;
    }

    @PostMapping("/transfer")
    public OperationId doTransfer(@RequestBody @Validated TransferMoneyDTO transferMoney) {
        log.info("doTransfer");
        System.out.println("doTransfer");
        return service.doTransfer(transferMoney);
    }

    @PostMapping("/confirmOperation")
    public OperationId confirmOperation(@RequestBody ConfirmOperation confirmOperation) {
        return service.confirmOperation(confirmOperation);
    }

}
