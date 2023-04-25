package com.bank.account.handler;

import com.bank.account.exceptions.*;
import com.bank.account.util.AccountDetailsErrorResponse;
import com.bank.account.util.AuditErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Класс, объединяющий в себе проверки всех кастомных исключений.
 * Отлавливает кастомные исключения во всех контроллерах
 */
@ControllerAdvice
@Slf4j
public class handlerAdvice {

    /**
     * Метод, отлавливает исключение {@link AccountDetailsNotFoundException}
     * @param e исключение типа {@link AccountDetailsNotFoundException}
     * @return объект типа {@link ResponseEntity}, в теле которого содержится объект класса {@link AccountDetailsErrorResponse} и HTTP статус NOT_FOUND
     */
    @ExceptionHandler
    private ResponseEntity<AccountDetailsErrorResponse> handleException (AccountDetailsNotFoundException e) {
        AccountDetailsErrorResponse response = new AccountDetailsErrorResponse(
                "Аккаунт с таким id не найден!",
                System.currentTimeMillis()
        );

        log.error("При поиске аккаунта произошла ошибка: {}", response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Метод, отлавливает исключение {@link AccountDetailsNotCreatedException}
     * @param e исключение типа {@link AccountDetailsNotCreatedException}
     * @return объект типа {@link ResponseEntity}, в теле которого содержится объект класса {@link AccountDetailsErrorResponse} и HTTP статус BAD_REQUEST
     */
    @ExceptionHandler
    private ResponseEntity<AccountDetailsErrorResponse> handleException (AccountDetailsNotCreatedException e) {
        AccountDetailsErrorResponse response = new AccountDetailsErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        log.error(response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Метод, отлавливает исключение {@link AccountDetailsNotUpdateException}
     * @param e исключение типа {@link AccountDetailsNotUpdateException}
     * @return объект типа {@link ResponseEntity}, в теле которого содержится объект класса {@link AccountDetailsErrorResponse} и HTTP статус NOT_FOUND
     */
    @ExceptionHandler
    private ResponseEntity<AccountDetailsErrorResponse> handleException (AccountDetailsNotUpdateException e) {
        AccountDetailsErrorResponse response = new AccountDetailsErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        log.error(response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Метод, отлавливает исключение {@link AccountDetailsNotFoundForUpdateException}
     * @param e исключение типа {@link AccountDetailsNotFoundForUpdateException}
     * @return объект типа {@link ResponseEntity}, в теле которого содержится объект класса {@link AccountDetailsErrorResponse} и HTTP статус NOT_FOUND
     */
    @ExceptionHandler
    private ResponseEntity<AccountDetailsErrorResponse> handleException (AccountDetailsNotFoundForUpdateException e) {
        AccountDetailsErrorResponse response = new AccountDetailsErrorResponse(
                "При обновлении, аккаунт с таким id не найден!",
                System.currentTimeMillis()
        );

        log.error("При обновлении аккаунта произошла ошибка: {}", response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Метод, отлавливает исключение {@link AuditNotFoundException}
     * @param e исключение типа {@link AuditNotFoundException}
     * @return объект типа {@link ResponseEntity}, в теле которого содержится объект класса {@link AuditErrorResponse} и HTTP статус NOT_FOUND
     */
    @ExceptionHandler
    private ResponseEntity<AuditErrorResponse> handleException(AuditNotFoundException e) {
        AuditErrorResponse response = new AuditErrorResponse(
                "Запись аудирования с таким id не найдена!",
                System.currentTimeMillis()
        );

        log.error("При поиске записи аудирования произошла ошибка: {}", response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Метод, отлавливает исключение {@link AuditNotCreatedException}
     * @param e исключение типа {@link AuditNotCreatedException}
     * @return объект типа {@link ResponseEntity}, в теле которого содержится объект класса {@link AuditErrorResponse} и HTTP статус BAD_REQUEST
     */
    @ExceptionHandler
    private ResponseEntity<AuditErrorResponse> handleException(AuditNotCreatedException e) {
        AuditErrorResponse response = new AuditErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        log.error(response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Метод, отлавливает исключение {@link AuditNotUpdateException}
     * @param e исключение типа {@link AuditNotUpdateException}
     * @return объект типа {@link ResponseEntity}, в теле которого содержится объект класса {@link AuditErrorResponse} и HTTP статус BAD_REQUEST
     */
    @ExceptionHandler
    private ResponseEntity<AuditErrorResponse> handleException(AuditNotUpdateException e) {
        AuditErrorResponse response = new AuditErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        log.error(response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
