package ru.netology.model;

/**
 * Интерфейс для создания паттерна Builder для объекта OperationInfo.
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
public interface OperationInfoBuilder {
    OperationInfoBuilder setOperationId(String operationId);
    OperationInfoBuilder setCardDebitNumber(String cardDebitNumber); //карта списания
    OperationInfoBuilder setCardTransferNumber(String cardTransferNumber); //карта зачисления
    OperationInfoBuilder setAmount(Amount amount);
    OperationInfoBuilder setCommission(int commission);
    OperationInfoBuilder setStatus(StatusOperation status);
    OperationInfo build();
}
