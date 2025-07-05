package ru.netology.logger;

import ru.netology.model.OperationInfo;

public interface OperationLogger {
    void logWrite(OperationInfo info);
}
