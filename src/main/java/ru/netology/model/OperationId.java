package ru.netology.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Этот класс хранит номер операции в виде стрки
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class OperationId {
    /**
     * Номер операции.
     */
    private String operationId;

}
