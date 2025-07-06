package ru.netology.exception;

/**
 * Этот собственные класс исключений унаследуемый от RuntimeException.
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
public class ErrorTransferException extends RuntimeException {
    /**
     * Конструктор.
     *
     * @param msg сообщение передаваемое при генерации исключения
     */
    public ErrorTransferException(String msg) {
        super(msg);
    }
}
