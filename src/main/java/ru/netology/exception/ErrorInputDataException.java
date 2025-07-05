package ru.netology.exception;

public class ErrorInputDataException extends RuntimeException{
    public ErrorInputDataException(String msg) {
        super(msg);
    }
}
