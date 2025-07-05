package ru.netology.exception;

public class ErrorTransferException extends RuntimeException{
    public ErrorTransferException(String msg) {
        super(msg);
    }
}
