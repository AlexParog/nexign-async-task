package com.nexign.asynctask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

/**
 * Исключение для случаев, когда задача не проходит валидацию.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationTaskException extends ApiException {

    /**
     * Конструктор для создания исключения ValidationTaskException с заданным сообщением и статусом.
     *
     * @param message Сообщение об ошибке.
     * @param status  HTTP статус ошибки.
     */
    public ValidationTaskException(String message, HttpStatus status) {
        super(message, status);
    }

    /**
     * Конструктор для создания исключения ValidationTaskException с форматированным сообщением.
     *
     * @param message Сообщение об ошибке.
     * @param args    Аргументы для форматирования сообщения.
     */
    public ValidationTaskException(String message, Object... args) {
        super(MessageFormat.format(message, args), HttpStatus.BAD_REQUEST);
    }
}
