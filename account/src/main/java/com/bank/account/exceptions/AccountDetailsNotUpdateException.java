package com.bank.account.exceptions;

/** Класс-исключение, необходим для информирования о том, что при обновлении сущности AccountDetails произошла ошибка */
public class AccountDetailsNotUpdateException extends RuntimeException {
    public AccountDetailsNotUpdateException(String msg) {
        super(msg);
    }
}
