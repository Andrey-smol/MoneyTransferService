package ru.netology.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Этот класс сущность данных при обработке операции
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class OperationEntity {
    /**
     * Ссылка на объкт CardEntity на который переводятся деньги.
     */
    private CardEntity cardTransfer;
    /**
     * Ссылка на объкт CardEntity с которого деньги снимаются.
     */
    private CardEntity cardDebit;

    /**
     * Ссылка на объкт Amount хранящий данные о сумме перевода и в какой валюте.
     */
    private Amount amount;
    /**
     * Комиссия при переводе снимаемая банком
     */
    private int commission;
    /**
     * Состояние данной операции.
     */
    private StatusOperation status;

}
