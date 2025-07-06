package ru.netology.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netology.commission.CalculateCommission;
import ru.netology.common.VerificationCode;
import ru.netology.converter.CurrencyConverter;
import ru.netology.exception.ErrorConfirmationException;
import ru.netology.exception.ErrorInputDataException;
import ru.netology.exception.ErrorTransferException;
import ru.netology.mapperDTO.Mapper;
import ru.netology.model.*;
import ru.netology.operation.Operation;
import ru.netology.repository.CardRepository;

import java.util.Optional;

/**
 * Класс реализующий бизнес логику приложения
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
@Service
@Slf4j
public class MoneyTransferService {
    /**
     * Ссылка на mapper.
     */
    private final Mapper mapper;
    /**
     * Ссылка на хранилище карт.
     */
    private final CardRepository cardRepository;
    /**
     * Ссылка на Component реализующий операции с картами.
     */
    private final Operation operation;
    /**
     * Ссылка на конвертер валют по курсу.
     */
    private final CurrencyConverter converter;

    @Autowired
    public MoneyTransferService(Mapper mapper, CardRepository rep, Operation operation, CurrencyConverter converter) {
        this.mapper = mapper;
        this.cardRepository = rep;
        this.operation = operation;
        this.converter = converter;
    }

    /**
     * Метод бизнес логики, проверка данных карт, подготовка транзакции и
     * получение id для операции
     *
     * @param transferMoneyDTO сущность хранящая данные транзакции,
     * входные данные преобразованные из json и переданные контроллером на обработку
     * @return OperationId возвращает номер операции
     * @throws ErrorTransferException - Не удалось сделать конвертацию валют для операции
     * Операция прервалась, из-за внутренней ошибки
     * Не удалось создать операцию перевода
     * @throws ErrorInputDataException - На карте не достаточно средств,
     * Карты с данным номером нет или она не действительна
     */
    public OperationId doTransfer(TransferMoneyDTO transferMoneyDTO) {
        if("RUR".equals(transferMoneyDTO.getAmount().getCurrency())){
            transferMoneyDTO.getAmount().setCurrency("RUB");
        }
        String cardToNumber = transferMoneyDTO.getCardToNumber();
        Amount amount = transferMoneyDTO.getAmount();
        if(cardToNumber.equals(transferMoneyDTO.getCardFromNumber())){
            throw new ErrorInputDataException("Номер карты с которой перевод совпадает с номером карты на которую перевод");
        }

        OperationId id;
        //есть ли карта списания
        Optional<CardEntity> optionalCardEntityFrom = cardRepository.findByCardEntity(mapper.toEntity(transferMoneyDTO));
        if (optionalCardEntityFrom.isPresent()) {
            //есть ли карта на которую переводим
            Optional<CardEntity> optionalCardEntityTo = cardRepository.findByCardNumber(cardToNumber);
            if (optionalCardEntityTo.isPresent()) {
                CardEntity cardEntityFrom = optionalCardEntityFrom.get();
                CardEntity cardEntityTo = optionalCardEntityTo.get();

                //карты есть начинаем операцию с картами, получаем id
                OperationEntity operationEntity = new OperationEntity(cardEntityTo, cardEntityFrom, amount, 0, StatusOperation.START);
                id = operation.add(operationEntity);
                System.out.println("карты есть начинаем операцию с картами, получаем id = " + id);
                if(id == null){
                    throw new ErrorTransferException("Не удалось создать операцию перевода");
                }

                //проверяем а достаточно ли средств на карте списания
                double sum = amount.getValue();
                //валюта списания совпадает ли со счётом на карте
                if (!cardEntityFrom.getCurrency().getCode().equals(amount.getCurrency())) {
                    //если не совпадает то надо конвертировать
                    if(!requestConvertCurrency(amount.getCurrency(), cardEntityFrom.getCurrency().getCode(), operationEntity)){
                        throw new ErrorTransferException(String.format("Не удалось сделать конвертацию валют для операции [%s]", id.getOperationId()));
                    }
                    sum = operationEntity.getAmount().getValue();
                    operation.updateById(id, operationEntity);
                }
                //надо подсчитать коммисию
                double commission = CalculateCommission.calculate(sum, cardEntityFrom.getCurrency());
                if (cardEntityFrom.getBalance() < sum + commission) {
                    throw new ErrorInputDataException(String.format("На карте с данным номером [%s] не достаточно средств", transferMoneyDTO.getCardFromNumber()));
                }

                operationEntity.setCommission((int)commission);
                operationEntity.setStatus(StatusOperation.IN_PROGRESS);
                Optional<OperationEntity> optional = operation.updateById(id, operationEntity);
                if(optional.isEmpty()){

                    throw new ErrorTransferException(String.format("Операция [%s] прервалась, из-за внутренней ошибки", id.getOperationId()));
                }
            } else {
                throw new ErrorInputDataException(String.format("Карты с данным номером [%s] нет или она не действительна", transferMoneyDTO.getCardToNumber()));
            }
        } else {
            throw new ErrorInputDataException(String.format("Карты с данным номером [%s] нет или она не действительна", transferMoneyDTO.getCardFromNumber()));
        }
        return id;
    }

    /**
     * Метод бизнес логики подтверждение транзакции
     *
     * @param confirmOperation сущность для подтверждения операции,
     * входные данные преобразованные из json и переданные контроллером на обработку
     * @return OperationId возвращает номер операции
     * @throws ErrorConfirmationException - Не верный код операции
     * Не верный код верификации для операции
     */
    public OperationId confirmOperation(ConfirmOperation confirmOperation) {
        if (VerificationCode.getVerificationCode().equals(confirmOperation.getCode())) {
            OperationId operationId = new OperationId(confirmOperation.getOperationId());
            Optional<OperationEntity> operationEntity = operation.getOperationById(operationId);
            if (operationEntity.isPresent()) {
                transfer(operationId, operationEntity.get());
            } else {
                throw new ErrorConfirmationException(String.format("Не верный код операции [%s]", confirmOperation.getOperationId()));
            }
        }else{
            throw new ErrorConfirmationException(String.format("Не верный код верификации для операции [%s]", confirmOperation.getOperationId()));
        }
        return new OperationId(confirmOperation.getOperationId());
    }

    /**
     * Внутренний метод перевода средств
     *
     * @param entity сущность хранящая данные операции,
     * @param id номер операции
     * @return
     * @throws ErrorConfirmationException - Операция прервалась из-за внутренней ошибки,
     * Операция прервалась, ошибка при снятии денег с карты,
     * Не удалось сделать конвертацию валют,
     * Операция прервалась, ошибка при переводе денег на карту,
     * Данный перевод с идентификатором закрыт
     */
    private void transfer(OperationId id, OperationEntity entity) {
        if(entity.getStatus() == StatusOperation.IN_PROGRESS) {
            //снимаем деньги со счета
            entity.setStatus(StatusOperation.WITHDRAWING_FROM_ACCOUNT);
            if(!operation.setStatus(id, StatusOperation.WITHDRAWING_FROM_ACCOUNT)){
                throw new ErrorConfirmationException(String.format("Операция [%s] прервалась из-за внутренней ошибки", id.getOperationId()));
            }
            if(!withdrawingMoneyFromAccount(entity)){
                throw new ErrorConfirmationException(String.format("Операция [%s] прервалась, ошибка при снятии денег с карты", id.getOperationId()));
            }
            //переводим на другой счёт
            int commission = entity.getCommission();
            entity.setCommission(0);
            entity.setStatus(StatusOperation.TRANSFER_TO_ACCOUNT);
            if(!operation.setStatus(id, StatusOperation.TRANSFER_TO_ACCOUNT)){
                throw new ErrorConfirmationException(String.format("Операция [%s] прервалась из-за внутренней ошибки", id.getOperationId()));
            }
            //надо ли сделать конвертацию валют
            if(!entity.getAmount().getCurrency().equals(entity.getCardTransfer().getCurrency().getCode())){
                if(!requestConvertCurrency(entity.getAmount().getCurrency(),
                        entity.getCardTransfer().getCurrency().getCode(),
                        entity)){
                    throw new ErrorConfirmationException(String.format("Не удалось сделать конвертацию валют для операции [%s]", id.getOperationId()));
                }
                operation.updateById(id, entity);
            }

            if(!addingMoneyToAccount(entity)){
                entity.setCommission(commission);
                if(!refundCaseOfError(entity)){
                    throw new ErrorConfirmationException(String.format("Операция [%s] прервалась, ошибка при переводе денег на карту," +
                            "к сожалению ваши деньги потеряны. Обратитесь в банк.", id.getOperationId()));
                }
                throw new ErrorConfirmationException(String.format("Операция [%s] прервалась, ошибка при переводе денег на карту", id.getOperationId()));
            }
            entity.setStatus(StatusOperation.DONE_SUCCESSFUL);
            operation.updateById(id, entity);
        }else{
            throw new ErrorConfirmationException(String.format("Данный перевод с идентификатором %s закрыт", id.getOperationId()));
        }
    }

    private boolean refundCaseOfError(OperationEntity entity){
        int balance = entity.getCardDebit().getBalance() + entity.getAmount().getValue() + entity.getCommission();
        entity.getCardDebit().setBalance(balance);
        Optional<CardEntity> optionalCardEntity = cardRepository.updateBalance(entity.getCardDebit());
        return optionalCardEntity.isPresent();
    }
    private boolean requestConvertCurrency(String currencyFrom, String currencyTo, OperationEntity entity){
        Optional<Double> optional = converter.convertCurrency((double)entity.getAmount().getValue(), currencyFrom, currencyTo);
        if(optional.isEmpty()){
            return false;
        }
        entity.getAmount().setValue(optional.get().intValue());
        entity.getAmount().setCurrency(currencyTo);
        return true;
    }
    private boolean withdrawingMoneyFromAccount(OperationEntity entity){
        int amount = entity.getAmount().getValue();
        int balance = entity.getCardDebit().getBalance() - amount - entity.getCommission();
        entity.getCardDebit().setBalance(balance);
        Optional<CardEntity> card = cardRepository.updateBalance(entity.getCardDebit());
        return card.isPresent();
    }
    private boolean addingMoneyToAccount(OperationEntity entity){
        int balance = entity.getCardTransfer().getBalance() + entity.getAmount().getValue();
        entity.getCardTransfer().setBalance(balance);
        Optional<CardEntity> optionalCardEntity = cardRepository.updateBalance(entity.getCardTransfer());
        return optionalCardEntity.isPresent();
    }
}
