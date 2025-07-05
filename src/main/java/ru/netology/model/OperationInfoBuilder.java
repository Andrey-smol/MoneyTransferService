package ru.netology.model;

public interface OperationInfoBuilder {
    OperationInfoBuilder setOperationId(String operationId);
    OperationInfoBuilder setCardDebitNumber(String cardDebitNumber); //карта списания
    OperationInfoBuilder setCardTransferNumber(String cardTransferNumber); //карта зачисления
    OperationInfoBuilder setAmount(Amount amount);
    OperationInfoBuilder setCommission(int commission);
    OperationInfoBuilder setStatus(StatusOperation status);
    OperationInfo build();
}
