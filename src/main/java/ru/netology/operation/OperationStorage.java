package ru.netology.operation;

import ru.netology.model.OperationEntity;

import java.util.Map;

/**
 * Интерфейс описывающий API для хранилища операций.
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
public interface OperationStorage {
    Map<Long, OperationEntity> getMap();

    long getId();

    long incrementId();
}
