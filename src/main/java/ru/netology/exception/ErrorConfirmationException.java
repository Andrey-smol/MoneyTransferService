package ru.netology.exception;

public class ErrorConfirmationException extends RuntimeException{
    public ErrorConfirmationException(String msg){
        super(msg);
    }
}
