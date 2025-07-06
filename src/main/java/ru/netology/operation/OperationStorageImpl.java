package ru.netology.operation;

import org.springframework.stereotype.Component;
import ru.netology.model.OperationEntity;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Класс который реализует интерфейс OperationStorage
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
@Component
public class OperationStorageImpl implements OperationStorage {
    /**
     * ConcurrentHashMap<Long, OperationEntity> хранилище всех операций с уникальным ключём
     */
    private final Map<Long, OperationEntity> operationList = new ConcurrentHashMap<>();
    /**
     * код операции
     */
    private final AtomicLong id = new AtomicLong(0);

    public OperationStorageImpl() {
        Optional<Long> max = operationList.keySet().stream().max(Long::compareTo);
        if (max.isPresent()) {
            id.set(max.get());
        }
    }

    /**
     * Метод возвращает ссылку на хранилище
     *
     * @return Map<Long, OperationEntity>
     */
    @Override
    public Map<Long, OperationEntity> getMap() {

        return operationList;
    }

    /**
     * Возвращает id последней операции
     *
     * @return long
     */
    @Override
    public long getId() {
        return id.get();
    }

    /**
     * Увеличивает id на единицу
     *
     * @return long
     */
    @Override
    public long incrementId() {
        return id.incrementAndGet();
    }
}
