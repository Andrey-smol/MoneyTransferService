package ru.netology.operation;

import ru.netology.model.OperationId;
import ru.netology.model.StatusOperation;

/**
 * Класс реализующий интерфейс Runnable для создания потоков выполнения.
 * Используется для наблюдения за выполняемыми операциями
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
public class CompletionOperationThread implements Runnable {
    /**
     * Ссылка на объкт OperationId хранящий номер операции.
     */
    private final OperationId operationId;
    /**
     * Время в секундах когда был запущен поток.
     */
    private final long currentTimeInSeconds = System.currentTimeMillis() / 1000;
    /**
     * Ссылка на Operation хранящий данные о операциях.
     */
    private final Operation operation;

    /**
     * Конструктор
     *
     * @param operationId
     * @param operation
     * @return
     */
    public CompletionOperationThread(OperationId operationId, Operation operation) {
        this.operationId = operationId;
        this.operation = operation;
    }

    @Override
    public void run() {
        while (operation.getStatus(operationId) != StatusOperation.DONE_ERROR && operation.getStatus(operationId) != StatusOperation.DONE_SUCCESSFUL) {
            if (System.currentTimeMillis() / 1000 - currentTimeInSeconds > 20) {
                operation.setStatus(operationId, StatusOperation.DONE_ERROR);
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
