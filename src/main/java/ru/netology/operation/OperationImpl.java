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

@Component
public class OperationImpl implements Operation {
    //коллекция карт с которых осуществляется перевод денег
    private final Set<String> cardsDebit = ConcurrentHashMap.newKeySet();
    private final OperationLogger operationLogger;
    private final OperationStorage operationStorage;
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    @Autowired
    public OperationImpl(OperationStorage operationStorage, OperationLogger operationLogger) {
        this.operationLogger = operationLogger;
        this.operationStorage = operationStorage;
    }

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

    @Override
    public Optional<OperationEntity> getOperationById(OperationId id) {
        OperationEntity entity = operationStorage.getMap().get(Long.parseLong(id.getOperationId()));
        return (entity == null) ? Optional.empty() : Optional.of(entity);
    }

    @Override
    public StatusOperation getStatus(OperationId id) {
        OperationEntity entity = operationStorage.getMap().get(Long.parseLong(id.getOperationId()));
        return (entity == null) ? null : entity.getStatus();
    }

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

    @Override
    public Optional<OperationEntity> removeById(OperationId id) {
        OperationEntity entity = operationStorage.getMap().remove(Long.parseLong(id.getOperationId()));
        return (entity == null) ? Optional.empty() : Optional.of(entity);
    }

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
