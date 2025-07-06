package ru.netology.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Этот класс хранит информацию о операции для передачи для записи в лог файл
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class OperationInfo {
    /**
     * Номер операции.
     */
    private String operationId;
    /**
     * Номер карты списания.
     */
    private String cardDebitNumber;
    /**
     * Номер карты зачисления.
     */
    private String cardTransferNumber;
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
