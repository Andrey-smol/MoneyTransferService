package ru.netology.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.netology.logger.OperationLogger;
import ru.netology.model.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Класс который реализует интерфейс Operation
 *
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
@Component
public class OperationImpl implements Operation {

    /**
     * коллекция Set<String> карт с которых осуществляется перевод денег.
     */
    private final Set<String> cardsDebit = ConcurrentHashMap.newKeySet();
    /**
     * Ссылка на объект записывающий логи операций.
     */
    private final OperationLogger operationLogger;
    /**
     * Ссылка на хранилище операций и их уникального номера.
     */
    private final OperationStorage operationStorage;
    /**
     * Управление пуллом потоков для отслеживания операций.
     */
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    /**
     * Конструктор
     *
     * @param operationStorage  хранилище операций
     * @param operationLogger   логирование операций
     * @return
     */
    @Autowired
    public OperationImpl(OperationStorage operationStorage, OperationLogger operationLogger) {
        this.operationLogger = operationLogger;
        this.operationStorage = operationStorage;
    }

    /**
     * добавление новой операции
     *
     * @param operationEntity сущность хранящая данные о операции
     * @return OperationId возвращает номер операции
     * или null если не получилось создать новую операцию
     */
    @Override
    public OperationId add(OperationEntity operationEntity) {
        if(checkingWithdrawalMoneyFromCard(operationEntity)){
            return null;
        }
        long id = operationStorage.incrementId();

        OperationEntity operation = operationStorage.getMap().put(id, operationEntity);
        if (operation != null) {
            return null;
        }
        addCardDebit(operationEntity);
        log(String.valueOf(id), operationEntity);
        OperationId operationId = new OperationId(String.valueOf(id));
        addIntoExecutorService(operationId);
        return operationId;
    }

    /**
     * Получение сущности хранящей данные о операции по номеру
     *
     * @param id номер запрашиваемой операции в виде объекта OperationId
     * @return Optional<OperationEntity> возвращает номер операции
     * или пустой объект если ре удалось найти
     */
    @Override
    public Optional<OperationEntity> getOperationById(OperationId id) {
        OperationEntity entity = operationStorage.getMap().get(Long.parseLong(id.getOperationId()));
        return (entity == null) ? Optional.empty() : Optional.of(entity);
    }

    /**
     * Получение статуса операции по номеру
     *
     * @param id номер запрашиваемой операции в виде объекта OperationId
     * @return StatusOperation - возвращает в виде объекта StatusOperation
     * или null если найти операцию не удалось
     */
    @Override
    public StatusOperation getStatus(OperationId id) {
        OperationEntity entity = operationStorage.getMap().get(Long.parseLong(id.getOperationId()));
        return (entity == null) ? null : entity.getStatus();
    }

    /**
     * Установка статуса операции
     *
     * @param id номер запрашиваемой операции в виде объекта OperationId
     * @param statusOperation тип статуса который над установить
     * @return true - при успешном смене статуса, false - при ошибке
     */
    @Override
    public boolean setStatus(OperationId id, StatusOperation statusOperation) {
        String oid = id.getOperationId();
        OperationEntity entity = operationStorage.getMap().get(Long.parseLong(oid));
        if (entity == null) {
            return false;
        }
        entity.setStatus(statusOperation);
        operationStorage.getMap().put(Long.parseLong(oid), entity);
        removeCardDebit(entity);
        log(oid, entity);
        return true;
    }

    /**
     * Удаление операции из хранилища
     *
     * @param id номер запрашиваемой операции в виде объекта OperationId
     * @return возвращается удалённая сущность OperationEntity обёрнутая в Optional
     * или если ошибка удаления Optional.empty()
     */
    @Override
    public Optional<OperationEntity> removeById(OperationId id) {
        OperationEntity entity = operationStorage.getMap().remove(Long.parseLong(id.getOperationId()));
        return (entity == null) ? Optional.empty() : Optional.of(entity);
    }

    /**
     * Обновление сущности операции по id
     *
     * @param id номер запрашиваемой операции в виде объекта OperationId
     * @param entity новая сущность для сохранения
     * @return возвращается обновлённая сущность OperationEntity обёрнутая в Optional
     * или если ошибка обновления Optional.empty()
     */
    @Override
    public Optional<OperationEntity> updateById(OperationId id, OperationEntity entity) {
        String oid = id.getOperationId();
        long key = Long.parseLong(oid);
        if (operationStorage.getMap().containsKey(key)) {
            log(oid, entity);
            operationStorage.getMap().put(key, entity);
            return Optional.of(entity);
        }
        return Optional.empty();
    }


    /**
     * Получение статуса операции по id
     *
     * @param id номер запрашиваемой операции в виде long
     * @return StatusOperation - возвращает в виде объекта StatusOperation
     * или null если найти операцию не удалось
     */
    @Override
    public StatusOperation getStatus(Long id) {
        OperationEntity entity = operationStorage.getMap().get(id);
        return (entity == null) ? null : entity.getStatus();
    }

    private void log(String operationId, OperationEntity entity) {
        operationLogger.logWrite(new OperationInfoBuilderImpl()
                .setOperationId(operationId)
                .setAmount(entity.getAmount())
                .setStatus(entity.getStatus())
                .setCardDebitNumber(entity.getCardDebit().getCardNumber())
                .setCardTransferNumber(entity.getCardTransfer().getCardNumber())
                .setCommission(entity.getCommission())
                .build());
    }

    protected void addIntoExecutorService(OperationId operationId) {
        pool.submit(new CompletionOperationThread(operationId, this));
    }

    private boolean checkingWithdrawalMoneyFromCard(OperationEntity operationEntity){
        return cardsDebit.contains(operationEntity.getCardDebit().getCardNumber());
    }

    private void addCardDebit(OperationEntity operationEntity){
        cardsDebit.add(operationEntity.getCardDebit().getCardNumber());
    }

    private void removeCardDebit(OperationEntity operationEntity){
        StatusOperation status = operationEntity.getStatus();
        if(status == StatusOperation.DONE_ERROR || status == StatusOperation.DONE_SUCCESSFUL) {
            cardsDebit.remove(operationEntity.getCardDebit().getCardNumber());
        }
    }
}
