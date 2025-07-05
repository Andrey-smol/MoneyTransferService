package ru.netology.operation;

import org.springframework.stereotype.Component;
import ru.netology.model.OperationEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class OperationStorageImpl implements OperationStorage{
    private final Map<Long, OperationEntity> operationList = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(0);

    @Override
    public Map<Long, OperationEntity> getMap() {

        return operationList;
    }

    @Override
    public long getId() {
        return id.get();
    }

    @Override
    public long incrementId() {
        return id.incrementAndGet();
    }
}
