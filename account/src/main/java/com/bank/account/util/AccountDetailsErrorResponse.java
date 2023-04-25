package com.bank.account.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс, который формирует ошибку при неправильной работе с {@link  com.bank.account.controllers.AccountDetailsRestController}
 */
@Data
@AllArgsConstructor
public class AccountDetailsErrorResponse {

    private String message;
    private long timestamp;
}
