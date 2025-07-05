package ru.netology.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationInfo {
    private String operationId;
    private String cardDebitNumber; //карта списания
    private String cardTransferNumber; //карта зачисления
    private Amount amount;
    private int commission;
    private StatusOperation status;
}
