package com.bank.account.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс, который формирует ошибку при неправильной работе с {@link  com.bank.account.controllers.AuditRestController}
 */
@Data
@AllArgsConstructor
public class AuditErrorResponse {

    private String message;
    private long timestamp;
}
