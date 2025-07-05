package ru.netology.operation;

import ru.netology.model.OperationId;
import ru.netology.model.StatusOperation;

public class CompletionOperationThread implements Runnable {
    private final OperationId operationId;
    private final long currentTimeInSeconds = System.currentTimeMillis() / 1000;
    private final Operation operation;

    public CompletionOperationThread(OperationId operationId, Operation operation) {
        this.operationId = operationId;
        this.operation = operation;
    }

    @Override
    public void run() {
        //System.out.println("Runnable id = " + operationId.getOperationId() + ", time = " + currentTimeInSeconds);
        while (operation.getStatus(operationId) != StatusOperation.DONE_ERROR && operation.getStatus(operationId) != StatusOperation.DONE_SUCCESSFUL) {
            if (System.currentTimeMillis() / 1000 - currentTimeInSeconds > 10) {
                operation.setStatus(operationId, StatusOperation.DONE_ERROR);
                //System.out.println("END id = " + operationId.getOperationId() + ", timeEnd = " + System.currentTimeMillis() / 1000);
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }
}
