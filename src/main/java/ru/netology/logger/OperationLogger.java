package ru.netology.logger;

import ru.netology.model.OperationInfo;

/**
 * Интерфейс, представляющий основные операции
 * для работы с объектом для записи логов операций.
 *
 * @author Андрей Кузавов
 * @version 1.0
 */
public interface OperationLogger {
    /**
     * Метод получает данные в виде объекта OperationInfo
     *
     * @param info входной объект который нужно записать в лог файл
     * @return
     */
    void logWrite(OperationInfo info);
}
