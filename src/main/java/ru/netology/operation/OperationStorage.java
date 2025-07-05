package ru.netology.operation;

import ru.netology.model.OperationEntity;

import java.util.Map;

public interface OperationStorage {
    Map<Long, OperationEntity> getMap();
    long getId();
    long incrementId();
}
