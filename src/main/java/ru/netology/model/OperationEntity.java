package ru.netology.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationEntity {
    private CardEntity cardTransfer;
    private CardEntity cardDebit;

    private Amount amount;
    private int commission;
    private StatusOperation status;

}
