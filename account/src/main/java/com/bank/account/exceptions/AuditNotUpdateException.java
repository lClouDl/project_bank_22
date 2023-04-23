package com.bank.account.exceptions;

/** Класс-исключение, необходим для информирования о том, что при обновлении сущности Audit произошла ошибка */
public class AuditNotUpdateException extends RuntimeException {
    public AuditNotUpdateException(String msg) {
        super(msg);
    }
}
