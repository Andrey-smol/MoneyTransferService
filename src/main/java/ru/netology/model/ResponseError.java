package ru.netology.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Этот класс ответа приложения в случае возникновения исключений
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class ResponseError {
    /**
     * Собщение об ошибке.
     */
    private String message;
    /**
     * Номер ошибки.
     */
    private int id;
}
