package ru.netology.exception;

/**
 * Этот собственные класс исключений унаследуемый от RuntimeException.
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
public class ErrorInputDataException extends RuntimeException{
    /**
     * Конструктор.
     *
     * @param msg сообщение передаваемое при генерации исключения
     */
    public ErrorInputDataException(String msg) {
        super(msg);
    }
}
