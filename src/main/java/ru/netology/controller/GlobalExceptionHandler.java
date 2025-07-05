package ru.netology.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.exception.ErrorConfirmationException;
import ru.netology.exception.ErrorInputDataException;
import ru.netology.exception.ErrorTransferException;
import ru.netology.model.ResponseError;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> catchResourceArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Ошибка валидации: " + ex.getMessage());
        return new ResponseEntity<>(getErrorResponse("Ошибка валидации: " + ex.getMessage(), HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorConfirmationException.class)
    public ResponseEntity<Object> catchResourceErrorConfirmationException(ErrorConfirmationException ex){
        log.error(ex.getMessage());
        return new ResponseEntity<>(getErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ErrorTransferException.class)
    public ResponseEntity<Object> catchResourceErrorTransferException(ErrorTransferException ex){
        log.error(ex.getMessage());
        return new ResponseEntity<>(getErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ErrorInputDataException.class)
    public ResponseEntity<Object> catchResourceErrorErrorInputDataException(ErrorInputDataException ex){
        log.error(ex.getMessage());
        return new ResponseEntity<>(getErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> catchResourceNumberFormatException(NumberFormatException ex){
        log.error(ex.getMessage());
        return new ResponseEntity<>(getErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private ResponseError getErrorResponse(String msg, HttpStatus status){
        return new ResponseError(msg, status.value());
    }
}
