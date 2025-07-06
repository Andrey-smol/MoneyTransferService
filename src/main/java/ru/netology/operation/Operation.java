package ru.netology.operation;

import ru.netology.model.OperationEntity;
import ru.netology.model.OperationId;
import ru.netology.model.StatusOperation;

import java.util.Optional;

/**
 * Интерфейс описывающий API для объекта хранящего данные о операциях.
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
public interface Operation {
    OperationId add(OperationEntity operationEntity);

    Optional<OperationEntity> getOperationById(OperationId id);

    StatusOperation getStatus(OperationId id);
    StatusOperation getStatus(Long id);
    boolean setStatus(OperationId id, StatusOperation statusOperation);
    Optional<OperationEntity> removeById(OperationId id);
    Optional<OperationEntity> updateById(OperationId id, OperationEntity entity);
}
