package ru.netology.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Этот класс данных приходящих при POST запросе (/confirmOperation) в RestController
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class ConfirmOperation {
    /**
     * Код операции
     */
    private String operationId;
    /**
     * верификационный код подтверждения операции
     */
    private String code;
}
