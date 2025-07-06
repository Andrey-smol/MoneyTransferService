package ru.netology.operation;

import ru.netology.logger.OperationLogger;
import ru.netology.model.OperationId;

public class MyOperation extends OperationImpl {

    public MyOperation(OperationStorage operationStorage, OperationLogger operationLogger) {
        super(operationStorage, operationLogger);
    }

    @Override
    protected void addIntoExecutorService(OperationId operationId) {
        return;
    }
}
