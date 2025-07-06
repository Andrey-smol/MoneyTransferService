package ru.netology.model;

/**
 * Это класс строитель для объекта OperationInfo
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
public class OperationInfoBuilderImpl implements OperationInfoBuilder {
    private String operationId;
    private String cardDebitNumber; //карта списания
    private String cardTransferNumber; //карта зачисления
    private Amount amount;
    private int commission = 0;
    private StatusOperation status;

    @Override
    public OperationInfoBuilder setOperationId(String operationId) {
        this.operationId = operationId;
        return this;
    }

    @Override
    public OperationInfoBuilder setCardDebitNumber(String cardDebitNumber) {
        this.cardDebitNumber = cardDebitNumber;
        return this;
    }

    @Override
    public OperationInfoBuilder setCardTransferNumber(String cardTransferNumber) {
        this.cardTransferNumber = cardTransferNumber;
        return this;
    }

    @Override
    public OperationInfoBuilder setAmount(Amount amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public OperationInfoBuilder setCommission(int commission) {
        this.commission = commission;
        return this;
    }

    @Override
    public OperationInfoBuilder setStatus(StatusOperation status) {
        this.status = status;
        return this;
    }

    @Override
    public OperationInfo build() {
        return new OperationInfo(operationId, cardDebitNumber, cardTransferNumber, amount, commission, status);
    }
}
