package com.bank.account.util;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс, который формирует ошибку при неправильной работе с {@link  com.bank.account.controllers.AuditRestController}
 */
@Getter
@Setter
public class AuditErrorResponse {

    private String message;
    private long timestamp;

    /**
     * Конструктор - создает объект класса {@link AuditErrorResponse}
     * @param message сообщение о природе ошибки
     * @param timestamp время регистрации ошибки
     */
    public AuditErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
